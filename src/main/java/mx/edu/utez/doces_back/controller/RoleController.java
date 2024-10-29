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

import mx.edu.utez.doces_back.model.RoleModel;
import mx.edu.utez.doces_back.service.RoleService;
import mx.edu.utez.doces_back.utils.Utilities;

@RestController
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;
    private static final String RECORD_NOT_FOUND = "Record not found.";
    private static final String INTERNAL_SERVER_ERROR = "An internal server error occurred.";

    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // GetAll
    @GetMapping("/role")
    public List<RoleModel> roles() {
        return this.roleService.getAll();
    }

    // GetById
    @GetMapping("/role/{id}")
    public RoleModel getById(@PathVariable("id") Integer id) {
        return this.roleService.findById(id);
    }

    // Post
    @PostMapping("/role")
    public ResponseEntity<Object> createRole(@RequestBody RoleModel request) {
        this.roleService.save(request);
        return Utilities.generateResponse(HttpStatus.OK, "Record created successfully.");
    }

    // Put
    @PutMapping("/role/{id}")
    public ResponseEntity<Object> editRole(@PathVariable("id") Integer id, @RequestBody RoleModel request) {
        try {
            RoleModel role = this.getById(id);
            if (role == null) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, RECORD_NOT_FOUND);
            } else {
                role.setName(request.getName());
                this.roleService.save(role);
                return Utilities.generateResponse(HttpStatus.OK, "Record updated successfully.");
            }
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping("/role/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable("id") Integer id) {
        try {
            RoleModel role = this.getById(id);
            if (role == null) {
                return Utilities.generateResponse(HttpStatus.BAD_REQUEST, RECORD_NOT_FOUND);
            } else {
                this.roleService.delete(id);
                return Utilities.generateResponse(HttpStatus.OK, "Record deleted successfully.");
            }
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.BAD_REQUEST, INTERNAL_SERVER_ERROR);
        }
    }

}
