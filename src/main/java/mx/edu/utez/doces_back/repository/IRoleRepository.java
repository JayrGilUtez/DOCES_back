package mx.edu.utez.doces_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.utez.doces_back.model.RoleModel;

import java.util.Optional;
public interface IRoleRepository extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findByName(String name);
}
