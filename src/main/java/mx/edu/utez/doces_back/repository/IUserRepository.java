package mx.edu.utez.doces_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.doces_back.model.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, Integer> {

    UserModel findByEmail(String email);

}
