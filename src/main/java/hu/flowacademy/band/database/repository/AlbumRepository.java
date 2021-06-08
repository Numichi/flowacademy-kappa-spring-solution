package hu.flowacademy.band.database.repository;

import hu.flowacademy.band.database.models.Album;
import hu.flowacademy.band.database.models.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    // Album-nak adunk egy "alias" nevet, ami legyen nagy "A".
    // Azért csináltam így, hogy ne kelljen feleslegesen egy Bandát lekérdezni és átadni. Mert lehet úgy is, ahogy a
    // kikommentelt interfész esetében is látod lentebb.
    //
    //      SELECT * FROM album   WHERE band_id   = ?
    @Query("SELECT A FROM Album A WHERE A.band.id = ?1") // ?1 az interfény hányadik-paramétere fog illeszkedni ide
    List<Album> findAllByBandId(int bandId); // interfész metódus neve lényegtelen, mert @Query-t alkalmazunk...

    // List<Album> findAllByBand(Band band);
}
