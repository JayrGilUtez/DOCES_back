package mx.edu.utez.doces_back.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.service.UserService;

@Component
public class UserLogin implements UserDetailsService {

    private final UserService usuarioService;

    UserLogin(UserService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = this.usuarioService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " no existe en el sistema");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
    }

}
