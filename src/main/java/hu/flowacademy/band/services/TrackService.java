package hu.flowacademy.band.services;

import hu.flowacademy.band.controllers.requests.TrackRequest;
import hu.flowacademy.band.controllers.responses.TrackDetailsResponse;
import hu.flowacademy.band.controllers.responses.TrackInformationResponse;
import hu.flowacademy.band.database.models.Album;
import hu.flowacademy.band.database.models.Provider;
import hu.flowacademy.band.database.models.Track;
import hu.flowacademy.band.database.repository.TrackRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @NonNull
    public Track getTrackById(int id) {
        return trackRepository.findById(id).orElseThrow();
    }

    public void add(Album album, TrackRequest trackRequest) {
        var track = Track.builder()
            .name(trackRequest.getName())
            .length(trackRequest.getLength())
            .price(trackRequest.getPrice())
            .album(album)
            .build();

        trackRepository.save(track);
    }

    /** Az alábbiak azért staticusak, mert nincs osztály szintű attribútum benne, de még is Track témához tartoznak. */

    @NonNull
    public static List<TrackInformationResponse> toTrackInfo(@NonNull Album album) {
        return album.getTrackList().stream()
            .map(TrackService::toTrackInfo)
            .collect(Collectors.toList());
    }

    @NonNull
    public static TrackInformationResponse toTrackInfo(@NonNull Track album) {
        return new TrackInformationResponse(album.getId(), album.getName(), album.getLength());
    }

    @NonNull
    public static TrackDetailsResponse toTrackDetails(@NonNull Track track) {
        return new TrackDetailsResponse(
            track.getId(),
            track.getName(),
            track.getLength(),
            track.getPrice(),
            track.getProviderSet().stream().map(Provider::getName).collect(Collectors.toList())
        );
    }
}
