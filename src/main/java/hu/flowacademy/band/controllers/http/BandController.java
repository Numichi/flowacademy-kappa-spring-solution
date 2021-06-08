package hu.flowacademy.band.controllers.http;

import hu.flowacademy.band.controllers.responses.AlbumInformationResponse;
import hu.flowacademy.band.controllers.responses.BandResponse;
import hu.flowacademy.band.services.AlbumService;
import hu.flowacademy.band.services.BandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/band")
public class BandController {

    private final BandService bandService;
    private final AlbumService albumService;

    public BandController(
        BandService bandService,
        AlbumService albumService
    ) {
        this.bandService = bandService;
        this.albumService = albumService;
    }

    @GetMapping
    public List<BandResponse> getAllBand() {
        return bandService.findAll().stream()
            .map(bandService::convert)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public List<AlbumInformationResponse> getAlbumByBandId(@PathVariable int id) {
        return albumService.getAllByBandId(id).stream()
            .map(AlbumService::albumToAlbumInfo)
            .collect(Collectors.toList());
    }
}
