package mx.edu.utez.doces_back.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.doces_back.model.RoleModel;
import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.repository.IRoleRepository;
import mx.edu.utez.doces_back.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class InitalConfig implements CommandLineRunner {
    private final RoleModel roleModel = new RoleModel();
    private final UserModel userModel = new UserModel();

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Optional<UserModel> adminUser = Optional.ofNullable(userRepository.findByEmail("admin@gmail.com"));
        adminUser.ifPresent(userRepository::delete);
        roleRepository.deleteAll();

        RoleModel adminRole = getOrSave(new RoleModel("ROLE_ADMIN"));
        RoleModel userRole = getOrSave(new RoleModel("ROLE_USER"));

        // Create an admin user
        UserModel user = new UserModel();
        user.setName("Jayr Gil");
        user.setLastname("Galicia Jiménez");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("$4dmin_123!"));
        // Set
        user.setRole(adminRole);
        userRepository.save(user);

    }

    public RoleModel getOrSave(RoleModel role) {
        Optional<RoleModel> foundRole = roleRepository.findByName(role.getName());
        return foundRole.orElseGet(() -> roleRepository.save(role));
    }

}
