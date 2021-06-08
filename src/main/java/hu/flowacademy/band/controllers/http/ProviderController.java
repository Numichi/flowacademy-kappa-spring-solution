package hu.flowacademy.band.controllers.http;

import hu.flowacademy.band.controllers.responses.ProviderResponse;
import hu.flowacademy.band.controllers.responses.TrackInformationResponse;
import hu.flowacademy.band.services.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    private final ProviderService providerService;

    ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public Collection<ProviderResponse> getAllProvider() {
        return ProviderService.toResponse(providerService.getAll());
    }

    @GetMapping("{providerId}")
    public Collection<TrackInformationResponse> getTracksByProvider(@PathVariable int providerId) {
        return providerService.getTracksByProvider(providerId);
    }
}
