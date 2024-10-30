package mx.edu.utez.doces_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.service.UserService;
import mx.edu.utez.doces_back.utils.Utilities;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private static final String RECORD_NOT_FOUND = "Record not found.";
    private static final String INTERNAL_SERVER_ERROR = "An internal server error occurred.";

    UserController(UserService userService) {
        this.userService = userService;
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
    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody UserModel request) {
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
                user.setStudentId(request.getStudentId());
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
                user.setPassword(request.getPassword());
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
