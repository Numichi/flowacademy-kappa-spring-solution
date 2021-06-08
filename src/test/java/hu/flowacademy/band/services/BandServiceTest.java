package hu.flowacademy.band.services;

import com.github.javafaker.Faker;
import hu.flowacademy.band.database.models.Album;
import hu.flowacademy.band.database.models.Band;
import hu.flowacademy.band.database.repository.BandRepository;
import hu.flowacademy.band.enums.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

/**
 * Mivel ez egy Unit teszt, így MINDEN dependecy-jét MOCK-oljuk, mert BÁRMI lehet ott.
 */
@SpringBootTest
class BandServiceTest {

    @MockBean
    private BandRepository repository;

    @Autowired
    private BandService service;

    private final Faker faker = new Faker(new Locale("hu"));

    /**
     * Csak a következőt tesztelem:
     *
     * @see BandService#findAll()
     */
    @Nested
    public class FindAllTest {

        /**
         * Nem szabad, hogy tartalmazzon értéket!
         */
        @Test
        void testIfEmpty() {
            given(repository.findAll()).willReturn(List.of());

            var result = service.findAll();

            assertEquals(0, result.size());
        }

        /**
         * Mindig 2 értéket kell, hogy tartalmazzon.
         */
        @Test
        void testWithData() {
            var dataResult = List.of(
                Band.builder().name(faker.rockBand().name()).genre(Genre.ROCK).build(),
                Band.builder().name(faker.rockBand().name()).genre(Genre.POP).build()
            );

            given(repository.findAll()).willReturn(dataResult);

            var result = service.findAll();

            assertEquals(2, result.size());
            assertEquals(dataResult.get(0), result.get(0));
            assertEquals(dataResult.get(1), result.get(1));
        }
    }

    /**
     * Csak a következőt tesztelem:
     *
     * @see BandService#findById(Integer)
     */
    @Nested
    public class FindByIdTest {

        /**
         * Azt teszteli, hogy a "findById" exception-t dob-e, ha üres adatot kap a repository-tól.
         *
         * @see Optional#orElseThrow()
         */
        @Test
        void testIfEmpty() {
            given(repository.findById(anyInt())).willReturn(Optional.empty());

            try {
                service.findById(1); // Itt Exception kell, hogy legyen!
                fail("Should throw exception"); // Ha nem, ne menjen át a teszten!!!
            } catch (NoSuchElementException exception) {
                // nothing
            }
        }

        /**
         * Azt teszteli, hogy a "findById", hogy vissza adja-e azt az értéket, amit a repository ad vissza.
         * Nem szabad mást is csinálnia vele! Mert, ha igen, akkor a "result" mást kell hogy tartalmazzon.
         */
        @Test
        void testWithContent() {
            var band = Band.builder().id(1).name(faker.rockBand().name()).genre(Genre.POP).build();

            given(repository.findById(band.getId())).willReturn(Optional.of(band));

            var result = service.findById(band.getId());

            assertEquals(band, result);
        }
    }
}