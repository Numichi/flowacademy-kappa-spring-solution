package hu.flowacademy.band.database.repository;

import hu.flowacademy.band.database.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
}
