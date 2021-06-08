package hu.flowacademy.band.controllers;

import com.github.javafaker.Faker;
import hu.flowacademy.band.database.models.Album;
import hu.flowacademy.band.database.models.Band;
import hu.flowacademy.band.database.models.Track;
import hu.flowacademy.band.database.repository.AlbumRepository;
import hu.flowacademy.band.database.repository.BandRepository;
import hu.flowacademy.band.database.repository.TrackRepository;
import hu.flowacademy.band.enums.Genre;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ő azért kell, mert @BeforeAll eredetileg egy static történet, de nekem el kellene érni a "port" attribútumot.
class BandControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    BandRepository bandRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    TrackRepository trackRepository;

    private final Faker faker = new Faker(new Locale("hu"));

    /**
     * Mivel randomizáljuk a portot (lásd: SpringBootTest.WebEnvironment.RANDOM_PORT), így valahogy meg kell
     * mondani a HTTP kliensnek, hogy mi az a port amin támadunk.
     * <p>
     * [Source]: https://stackoverflow.com/questions/32054274/connection-refused-with-rest-assured-junit-test-case
     */
    @BeforeAll
    public void beforeClass() {
        RestAssured.port = port;
    }

    @BeforeEach
    public void setUp() throws SQLException {
        // törlöm a tábla tartalmát
        bandRepository.deleteAll();
        albumRepository.deleteAll();

        // úgy nehéz 1-es ID-ra keresni, ha AUTO INCREMENET az előző teszt számozását folytatja... ^^
        // Így kézzel vissza konfigurálom minden teszt elött
        //
        // Persze van olyan megoldás is, hogy újra húzod az adatbázist, de az rengeteg idő... -.-'
        // Lásd őt: https://stackoverflow.com/questions/34617152/how-to-re-create-database-before-each-test-in-spring
        dataSource.getConnection().prepareStatement("ALTER TABLE band AUTO_INCREMENT = 1").execute();
        dataSource.getConnection().prepareStatement("ALTER TABLE album AUTO_INCREMENT = 1").execute();
    }

    /**
     * Külön metódusban töltöm fel a DB-t, hogy tudjam tesztelni, mi van akkor: ha nincs
     * Az AUTO_INCREMENT miatt az ID a sorrendszerint adott, így az nincs kitöltve.
     */
    private void insert() {
        // Test class alapértelmezett adatai
        var saveBand = bandRepository.saveAll(List.of(
            Band.builder().name(faker.rockBand().name()).genre(Genre.ROCK).build(),  // Neki lesz 3 albuma
            Band.builder().name(faker.rockBand().name()).genre(Genre.METAL).build(), // Neki lesz 2 albuma
            Band.builder().name(faker.rockBand().name()).genre(Genre.POP).build(),
            Band.builder().name(faker.rockBand().name()).genre(Genre.REP).build(),
            Band.builder().name(faker.rockBand().name()).genre(Genre.POP).build()
        ));

        var saveAlbums = albumRepository.saveAll(List.of(
            Album.builder().name(faker.rockBand().name()).release(LocalDate.now()).band(saveBand.get(0)).build(),  // Ebben lesz 2 szám
            Album.builder().name(faker.rockBand().name()).release(LocalDate.now()).band(saveBand.get(0)).build(),  // Ebben lesz 1 szám
            Album.builder().name(faker.rockBand().name()).release(LocalDate.now()).band(saveBand.get(0)).build(),  // Ebben lesz 0 szám
            Album.builder().name(faker.rockBand().name()).release(LocalDate.now()).band(saveBand.get(1)).build(),
            Album.builder().name(faker.rockBand().name()).release(LocalDate.now()).band(saveBand.get(1)).build()
        ));

        trackRepository.saveAll(List.of(
            Track.builder().price(null).name(faker.rockBand().name()).album(saveAlbums.get(0)).length(100).build(),
            Track.builder().price(null).name(faker.rockBand().name()).album(saveAlbums.get(0)).length(200).build(),
            Track.builder().price(null).name(faker.rockBand().name()).album(saveAlbums.get(1)).length(500).build()
        ));
    }

    @Test
    @DisplayName("Összes együttes -- 0 együttes esetén")
    void testGetAllBandWithEmptyDB() {
        // nem hívom meg az insert() metórust, így üres marad az adatbázis

        when().get("/api/band").then()
            .statusCode(200) // Válasz kód 200 kell, hogy legyen.
            .body("size()", is(0)); // Visszaadott dömb 5 eleműnek kell lennie.
    }

    @Test
    @DisplayName("Összes együttes -- 5 együttes esetén")
    void testGetAllBand() {
        insert();

        when().get("/api/band").then()
            .statusCode(200) // Válasz kód 200 kell, hogy legyen.
            .body("size()", is(5)); // Visszaadott dömb 5 eleműnek kell lennie.
    }

    @Test
    @DisplayName("Egy adott együttes albumai")
    void getOne() {
        insert();

        var band1 = bandRepository.findById(1).orElseThrow();  // ROCK

        var release = LocalDate.now();
        var albums = List.of(
            Album.builder().id(1).name(faker.name().name()).release(release).band(band1).build(),
            Album.builder().id(2).name(faker.name().name()).release(release).band(band1).build()
        );

        albumRepository.saveAll(albums);

        when().get("/api/band/1").then()
            .statusCode(200)
            // find { it.id == 1 } szintaxis egyenértékű egy: .filter(x -> x.id == 1) csak nem egy elemű tömböt ad vissza,
            // hanem konkrétan egy objektumot.
            // Az nem teszteltem le, hogy mi van akkor, ha 2db id == 1 van!!! Hogy fog működni? (költői)
            .body("size()", is(3)) // 3 albumnak kell lennie, mert "saveBand.get(0)"-t 3 albumnak adtam oda fentebb..
            .body("find { it.id == 1 }.name", is(albums.get(0).getName()))
            // Azért 300, mert a "saveAlbums.get(0)"-val hozok létre 2 track-et
            .body("find { it.id == 1 }.seconds", is(300));
    }
}