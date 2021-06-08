package hu.flowacademy.band.services;

import hu.flowacademy.band.controllers.responses.ProviderResponse;
import hu.flowacademy.band.controllers.responses.TrackInformationResponse;
import hu.flowacademy.band.database.models.Provider;
import hu.flowacademy.band.database.repository.ProviderRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @NonNull
    public List<Provider> getAll() {
        return providerRepository.findAll();
    }

    @NonNull
    public Collection<TrackInformationResponse> getTracksByProvider(int providerId) {
        var provider = providerRepository.findById(providerId).orElseThrow();
        return provider.getTrackSet().stream().map(TrackService::toTrackInfo).collect(Collectors.toSet());
    }

    @NonNull
    public static Set<ProviderResponse> toResponse(@NonNull Collection<Provider> providers) {
        return providers.stream()
            .map(it -> new ProviderResponse(it.getId(), it.getName()))
            .collect(Collectors.toSet());
    }
}
