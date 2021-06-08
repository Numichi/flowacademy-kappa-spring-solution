package hu.flowacademy.band.database.repository;

import hu.flowacademy.band.database.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}
