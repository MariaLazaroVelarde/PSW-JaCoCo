package pe.edu.vallegrande.quality.repository;

import pe.edu.vallegrande.quality.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UserRepository {

    // Usamos CopyOnWriteArrayList para seguridad en concurrencia (opcional).
    private final List<User> users = new CopyOnWriteArrayList<>();

    // Retornamos una copia inmutable para evitar exposición directa.
    public List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(users));
    }

    public User save(User user) {
        // Si existe, actualizamos; si no, agregamos.
        findById(user.getId()).ifPresentOrElse(existingUser  -> {
            users.remove(existingUser );
            users.add(user);
        }, () -> users.add(user));
        return user;
    }

    public Optional<User> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return users.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    public boolean deleteById(String id) {
        return users.removeIf(u -> id != null && id.equals(u.getId()));
    }
}