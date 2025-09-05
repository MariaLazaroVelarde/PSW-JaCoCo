package pe.edu.vallegrande.quality.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;

    // Inyección por constructor (mejor práctica)
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>(repo.findAll());  // Copia mutable
        users.sort(Comparator.comparing(User::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
        return users;
    }

    public User create(@Valid @NotNull User user) {
        validateUser (user);

        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }

        if (user.getAge() == null) {
            user.setAge(0); // Constante podría definirse si se usa en varios lugares
        }

        return repo.save(user);
    }

    public Optional<User> find(String id) {
        return repo.findById(id);
    }

    public boolean remove(String id) {
        return repo.deleteById(id);
    }

    // Validación extraída para evitar duplicación y mejorar legibilidad
    private void validateUser (User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email is invalid");
        }
    }
}