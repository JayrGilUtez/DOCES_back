package mx.edu.utez.doces_back.repository;

import mx.edu.utez.doces_back.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPasswordResetToken extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
}
