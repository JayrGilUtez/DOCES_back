package mx.edu.utez.doces_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import mx.edu.utez.doces_back.model.RoleModel;
import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.repository.IRoleRepository;
import mx.edu.utez.doces_back.service.UserService;
import mx.edu.utez.doces_back.utils.Utilities;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final IRoleRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String RECORD_NOT_FOUND = "Record not found.";
    private static final String INTERNAL_SERVER_ERROR = "An internal server error occurred.";

    UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, IRoleRepository repository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    // GetAll
    @GetMapping("/user")
    public List<UserModel> users() {
        return this.userService.getAll();
    }

    // GetById
    @GetMapping("/user/{id}")
    public UserModel getById(@PathVariable("id") Integer id) {
        return this.userService.findById(id);
    }

    // Post
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody UserModel request) {
        String defaultRoleName = "ROLE_USER";
        RoleModel defaultRole = repository.findByName(defaultRoleName)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        request.setRole(defaultRole);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        this.userService.save(request);
        return Utilities.generateResponse(HttpStatus.OK, "Record created successfully.");
    }

    // Put
    @PutMapping("/user/{id}")
    public ResponseEntity<Object> editUser(@PathVariable("id") Integer id, @RequestBody UserModel request) {
        try {
            UserModel user = this.getById(id);
            if (user == null) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, RECORD_NOT_FOUND);
            } else {
                user.setName(request.getName());
                user.setLastname(request.getLastname());
                user.setMatricula(request.getMatricula());
                user.setEmail(request.getEmail());
                user.setRole(request.getRole());
                this.userService.save(user);
                return Utilities.generateResponse(HttpStatus.OK, "Record updated successfully.");
            }
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, INTERNAL_SERVER_ERROR);
        }
    }

    // Put Pasword
    @PutMapping("/user/{id}/password")
    public ResponseEntity<Object> editPassword(@PathVariable("id") Integer id, @RequestBody UserModel request) {
        try {
            UserModel user = this.getById(id);
            if (user == null) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, RECORD_NOT_FOUND);
            } else {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                this.userService.save(user);
                return Utilities.generateResponse(HttpStatus.OK, "Password updated successfully.");
            }
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer id) {
        try {
            UserModel user = this.getById(id);
            if (user == null) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, RECORD_NOT_FOUND);
            } else {
                this.userService.delete(id);
                return Utilities.generateResponse(HttpStatus.OK, "Record deleted successfully.");
            }
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, INTERNAL_SERVER_ERROR);
        }
    }
}
