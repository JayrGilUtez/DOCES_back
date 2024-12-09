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
        RoleModel adminRole = new RoleModel();
        adminRole.setId(1);
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        RoleModel userRole = new RoleModel();
        userRole.setId(2);
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

        Optional<UserModel> adminUser = Optional.ofNullable(userRepository.findByEmail("admin@gmail.com"));
        if (adminUser.isPresent()) {
            userRepository.delete(adminUser.get());
        }

        UserModel user = new UserModel();
        user.setId(1);
        user.setName("admin");
        user.setLastname("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("$4dmin_123!"));
        user.setRole(adminRole);
        userRepository.save(user);

    }

    public RoleModel getOrSave(RoleModel role) {
        Optional<RoleModel> foundRole = roleRepository.findByName(role.getName());
        return foundRole.orElseGet(() -> roleRepository.save(role));
    }

}
