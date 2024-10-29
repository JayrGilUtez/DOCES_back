package mx.edu.utez.doces_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.doces_back.model.RoleModel;

public interface IRoleRepository extends JpaRepository<RoleModel, Integer> {

}
