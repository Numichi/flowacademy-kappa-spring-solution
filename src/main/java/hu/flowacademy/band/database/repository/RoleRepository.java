package hu.flowacademy.band.database.repository;

import hu.flowacademy.band.database.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
