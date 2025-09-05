package pe.edu.vallegrande.quality.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    // Inyección por constructor (mejor práctica)
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser (@Valid @RequestBody User user) {
        try {
            User createdUser  = service.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User  not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable String id) {
        boolean deleted = service.remove(id);
        if (deleted) {
            return ResponseEntity.ok("User  deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User  not found");
        }
    }
}