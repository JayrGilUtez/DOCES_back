package mx.edu.utez.doces_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.edu.utez.doces_back.jwt.AuthRequest;
import mx.edu.utez.doces_back.jwt.AuthResponse;
import mx.edu.utez.doces_back.jwt.JwtTokenUtil;
import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.service.UserService;

@RestController
@RequestMapping("/api")
public class AccessController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    AccessController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
            mx.edu.utez.doces_back.service.UserService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            logger.warn("{}", authentication);

            UserModel user = this.userService.findByEmail(request.getEmail());
            String accessToken = this.jwtTokenUtil.generatedToken(user);
            AuthResponse response = new AuthResponse(request.getEmail(), accessToken);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            logger.warn("{}", authentication);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
