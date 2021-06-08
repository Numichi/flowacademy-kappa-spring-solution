package hu.flowacademy.band.database.repository;

import hu.flowacademy.band.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    // SELECT * FROM user WHERE 'email' = ? -- Kérdő jel helyére a "email" kerül
    Optional<User> findByEmail(String email);
}
