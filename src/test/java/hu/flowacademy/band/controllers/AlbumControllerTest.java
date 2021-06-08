package hu.flowacademy.band.controllers;

import com.github.javafaker.Faker;
import hu.flowacademy.band.controllers.errors.AdviceController;
import hu.flowacademy.band.controllers.responses.AlbumInformationResponse;
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
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ő azért kell, mert @BeforeAll eredetileg egy static történet, de nekem el kellene érni a "port" attribútumot.
class AlbumControllerTest {

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
     *
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
        dataSource.getConnection().prepareStatement("ALTER TABLE track AUTO_INCREMENT = 1").execute();
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

        // Itt most nem Faker-ezek, mert most nevével azonosítóm, hogy jót kapok-e vissza. Lásd teszt.
        var saveAlbums = albumRepository.saveAll(List.of(
            Album.builder().name("Album1").release(LocalDate.now()).band(saveBand.get(0)).build(),  // Ebben lesz 2 szám
            Album.builder().name("Album2").release(LocalDate.now()).band(saveBand.get(0)).build(),  // Ebben lesz 1 szám
            Album.builder().name("Album3").release(LocalDate.now()).band(saveBand.get(0)).build(),  // Ebben lesz 0 szám
            Album.builder().name("Album4").release(LocalDate.now()).band(saveBand.get(1)).build(),
            Album.builder().name("Album5").release(LocalDate.now()).band(saveBand.get(1)).build()
        ));

        trackRepository.saveAll(List.of(
            Track.builder().price(null).name("Track1").album(saveAlbums.get(0)).length(100).build(),
            Track.builder().price(null).name("Track2").album(saveAlbums.get(0)).length(200).build(),
            Track.builder().price(null).name("Track3").album(saveAlbums.get(1)).length(500).build()
        ));
    }

    @Test
    @DisplayName("Album1 esetén")
    void testAlbum1() {
        insert();

        // Lekérem az 1-es ID-s band-det
        var when = when().get("/api/band/1");

        // aminek HTTP 200-nak kell lennie
        when.then().statusCode(200);

        // Az adatot visszanyerem objektum szintren
        var albums = when.as(AlbumInformationResponse[].class);

        // Kiválasztom az 1-es albumot, ami a tömb 0-ás eleme lesz. (Annak kellene lennie...)
        var anAlbum = albums[0];

        // Azért ellenőrizzük le.
        assertEquals(anAlbum.getId(), 1);
        assertEquals(anAlbum.getName(), "Album1");

        // Akkor a teszt, amiért írtuk mindezt. :D
        when().get("/api/album/{id}", anAlbum.getId()).then()
            .statusCode(200)
            .body("size()", is(2)) // Ebben két számnak kell lennie
            .body("name", contains("Track1", "Track2"));
    }

    /**
     * Igen jól látod... Ez egy copy-paste. ^^
     */
    @Test
    @DisplayName("Album2 esetén")
    void testAlbum2() {
        insert();

        // Lekérem az 1-es ID-s band-det
        var when = when().get("/api/band/1");

        // aminek HTTP 200-nak kell lennie
        when.then().statusCode(200);

        // Az adatot visszanyerem objektum szintren
        var albums = when.as(AlbumInformationResponse[].class);

        // Kiválasztom az 2-es albumot, ami a tömb 1-es eleme lesz. (Annak kellene lennie...)
        var anAlbum = albums[1];

        // Azért ellenőrizzük le.
        assertEquals(anAlbum.getId(), 2);
        assertEquals(anAlbum.getName(), "Album2");

        // Akkor a teszt, amiért írtuk mindezt. :D
        when().get("/api/album/{id}", anAlbum.getId()).then()
            .statusCode(200)
            .body("size()", is(1)) // Ebben 1 számnak kell lennie
            .body("name", contains("Track3"));
    }

    /**
     * Igen jól látod... Ez is egy copy-paste. ^^
     */
    @Test
    @DisplayName("Album3 esetén")
    void testAlbum3() {
        insert();

        // Lekérem az 1-es ID-s band-det
        var when = when().get("/api/band/1");

        // aminek HTTP 200-nak kell lennie
        when.then().statusCode(200);

        // Az adatot visszanyerem objektum szintren
        var albums = when.as(AlbumInformationResponse[].class);

        // Kiválasztom az 2-es albumot, ami a tömb 1-es eleme lesz. (Annak kellene lennie...)
        var anAlbum = albums[2];

        // Azért ellenőrizzük le. Nem szabadna lennie benne számnak.
        assertEquals(anAlbum.getId(), 3);
        assertEquals(anAlbum.getName(), "Album3");

        // Akkor a teszt, amiért írtuk mindezt. :D
        when().get("/api/album/{id}", anAlbum.getId()).then()
            .statusCode(200)
            .body("size()", is(0));
    }

    /**
     * Köhöm... BECSAPÓS!!! 4-es album már a 2-es bandához tartozik!!!
     * Tehát most olyan ID-t keresek, ami biztos nincs! Tehát: 6-os :)
     *
     * @see AdviceController#notFound()
     */
    @Test
    @DisplayName("Nem létező album") //
    void testNotExistAlbum() {
        insert(); // Nyilván, ha ezt kiveszem, akkor semmi sem létezik. Igen. Lehet így is tesztelni, hogy üres DB-n hívsz valamit.

        // Tudjuk, hogy nincs 6-es album, így ennek Exception-t kellene dobnia, amit elkapatunk az Advice-al. Lásd a commentben a @see-t.
        when().get("/api/album/{id}", 6).then()
            .statusCode(404);
    }
}