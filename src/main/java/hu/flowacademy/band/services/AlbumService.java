package hu.flowacademy.band.services;

import hu.flowacademy.band.ConstantValues;
import hu.flowacademy.band.controllers.requests.AlbumRequest;
import hu.flowacademy.band.controllers.responses.AlbumInformationResponse;
import hu.flowacademy.band.database.models.Album;
import hu.flowacademy.band.database.models.Band;
import hu.flowacademy.band.database.models.Track;
import hu.flowacademy.band.database.repository.AlbumRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }


    @NonNull
    public Album getById(int albumId) {
        return albumRepository.findById(albumId).orElseThrow();
    }

    @NonNull
    public List<Album> getAllByBandId(int id) {
        return albumRepository.findAllByBandId(id);
    }

    public void add(@NonNull Band band, @NonNull AlbumRequest album) {
        var newAlbum = Album.builder()
            .release(LocalDate.parse(album.getRelease(), ConstantValues.dateFormatter))
            .name(album.getName())
            .band(band)
            .build();

        albumRepository.save(newAlbum);
    }

    /**
     * Azért static, mert nincs osztály szintű attribútum benne. Így felesleges a példányának részének lennie.
     */
    @NonNull
    public static AlbumInformationResponse albumToAlbumInfo(@NonNull Album album) {
        // 1. sor: LAZY Query
        // 2. sor: Stream-et csinálunk a Listából
        // 3. sor: Minden elemet át "map"-olok egy int-re
        // 4. sor: Mivel minden elem int-tet tárol, így csinálok belőle egy IntStream-et
        // 5. sor: IntStream támogatja a Summázást. :)
        var length = album.getTrackList()
            .stream()
            .map(Track::getLength)
            .mapToInt(Integer::intValue)
            .sum();

        return new AlbumInformationResponse(album.getId(), album.getName(), length);
    }
}
