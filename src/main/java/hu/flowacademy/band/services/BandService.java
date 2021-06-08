package hu.flowacademy.band.services;

import hu.flowacademy.band.controllers.responses.BandResponse;
import hu.flowacademy.band.database.models.Band;
import hu.flowacademy.band.database.repository.BandRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BandService {

    private final BandRepository bandRepository;

    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    @NonNull
    @Transactional
    public List<Band> findAll() {
        return bandRepository.findAll();
    }

    @NonNull
    @Transactional
    public Band findById(@NonNull Integer id) {
        return bandRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Band save(Band band) {
        return bandRepository.save(band);
    }

    @NonNull
    public BandResponse convert(@NonNull Band band) {
        return new BandResponse(band.getId(), band.getName(), band.getGenre());
    }
}
