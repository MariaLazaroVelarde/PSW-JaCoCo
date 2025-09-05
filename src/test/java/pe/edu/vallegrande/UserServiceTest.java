package pe.edu.vallegrande;

import org.junit.jupiter.api.*;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;
import pe.edu.vallegrande.quality.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService service;

    @BeforeEach
    void setup() {
        System.out.println("Iniciando prueba...");
        // Usamos un repositorio en memoria nuevo para cada prueba
        UserRepository repo = new UserRepository();
        service = new UserService(repo);
    }

    @AfterEach
    void cleanup() {
        System.out.println("Prueba finalizada!");
    }

    @Test
    void testCreateUser () {
        System.out.println("Ejecutando testCreateUser ...");
        User user = new User(null, "María Lazaro", "maria@example.com", 20);
        User created = service.create(user);
        System.out.println("Usuario creado: " + created);
        assertNotNull(created.getId(), "El id no debe ser nulo");
        assertEquals("María Lazaro", created.getName());
        assertEquals("maria@example.com", created.getEmail());
        assertEquals(20, created.getAge());
    }

    @Test
    void testCreateUserInvalidName() {
        System.out.println("Ejecutando testCreateUser InvalidName...");
        User user = new User(null, "", "maria@example.com", 20);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.create(user);
        });
        System.out.println("Excepción capturada: " + ex.getMessage());
        assertEquals("Name is required", ex.getMessage());
    }

    @Test
    void testCreateUserInvalidEmail() {
        System.out.println("Ejecutando testCreateUser InvalidEmail...");
        User user = new User(null, "María", "mariaexample.com", 20);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.create(user);
        });
        System.out.println("Excepción capturada: " + ex.getMessage());
        assertEquals("Email is invalid", ex.getMessage());
    }
}