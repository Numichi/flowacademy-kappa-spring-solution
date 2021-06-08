package hu.flowacademy.band.controllers.http;

import hu.flowacademy.band.controllers.requests.TrackRequest;
import hu.flowacademy.band.controllers.responses.TrackDetailsResponse;
import hu.flowacademy.band.services.AlbumService;
import hu.flowacademy.band.services.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/track")
public class TrackController {

    private final TrackService trackService;
    private final AlbumService albumService;

    public TrackController(
        TrackService trackService,
        AlbumService albumService
    ) {
        this.trackService = trackService;
        this.albumService = albumService;
    }

    @GetMapping("/{trackId}")
    public TrackDetailsResponse getDetails(@PathVariable int trackId) {
        return TrackService.toTrackDetails(trackService.getTrackById(trackId));
    }

    @PostMapping("/{albumId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTrack(@PathVariable int albumId, @RequestBody @Valid @NonNull TrackRequest trackRequest) {
        var album = albumService.getById(albumId);
        trackService.add(album, trackRequest);
    }
}
