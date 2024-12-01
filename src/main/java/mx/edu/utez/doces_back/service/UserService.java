package mx.edu.utez.doces_back.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import mx.edu.utez.doces_back.model.PasswordResetToken;
import mx.edu.utez.doces_back.repository.IPasswordResetToken;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.repository.IUserRepository;

@Service
@Primary
@Transactional
public class UserService {

    private final IUserRepository repository;
    private final IPasswordResetToken passwordRepository;

    UserService(IUserRepository repository, IPasswordResetToken passwordRepository) {
        this.repository = repository;
        this.passwordRepository = passwordRepository;
    }

    public List<UserModel> getAll() {
        return this.repository.findAll(Sort.by("id").descending());
    }

    public UserModel findById(Integer id) {
        Optional<UserModel> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public UserModel findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public void save(UserModel user) {
        this.repository.save(user);
    }

    public void delete(Integer id) {
        this.repository.deleteById(id);
    }

    public void savePasswordResetToken(UserModel user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        passwordRepository.save(resetToken);
    }
}
