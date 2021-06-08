package hu.flowacademy.band.controllers.http;

import hu.flowacademy.band.controllers.requests.AlbumRequest;
import hu.flowacademy.band.controllers.responses.TrackInformationResponse;
import hu.flowacademy.band.services.AlbumService;
import hu.flowacademy.band.services.BandService;
import hu.flowacademy.band.services.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    private final AlbumService albumService;
    private final BandService bandService;

    AlbumController(
        AlbumService albumService,
        BandService bandService
    ) {
        this.albumService = albumService;
        this.bandService = bandService;
    }

    @GetMapping("/{albumId}")
    public List<TrackInformationResponse> getTrackInfoByAlbum(@PathVariable int albumId) {
        return TrackService.toTrackInfo(albumService.getById(albumId));
    }

    @PostMapping("/{bandId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAlbumToBand(@PathVariable int bandId, @RequestBody @Valid @NonNull AlbumRequest album) {
        var band = bandService.findById(bandId);
        albumService.add(band, album);
    }
}
