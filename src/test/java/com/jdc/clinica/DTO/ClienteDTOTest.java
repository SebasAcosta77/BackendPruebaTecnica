package com.jdc.clinica.DTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testClienteValido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre("Juan Pérez");
        cliente.setEmail("correo@correo.com");
        cliente.setTelefono("3124567890");

        Set<ConstraintViolation<ClienteDTO>> violaciones = validator.validate(cliente);

        assertTrue(violaciones.isEmpty(), "No debería fallar, datos válidos");
    }

    @Test
    void testNombreVacio() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre(""); // inválido
        cliente.setEmail("correo@correo.com");
        cliente.setTelefono("3211234567");

        Set<ConstraintViolation<ClienteDTO>> violaciones = validator.validate(cliente);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("El nombre del cliente es obligatorio")));
    }

    @Test
    void testEmailInvalido() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre("Cliente valido");
        cliente.setEmail("correo_invalido"); // no válido
        cliente.setTelefono("3009876543");

        Set<ConstraintViolation<ClienteDTO>> violaciones = validator.validate(cliente);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("El email no tiene un formato válido")));
    }

    @Test
    void testNombreMuyLargo() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre("x".repeat(101)); // ❌ supera 100 caracteres
        cliente.setEmail("test@mail.com");
        cliente.setTelefono("3012233445");

        Set<ConstraintViolation<ClienteDTO>> violaciones = validator.validate(cliente);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("no debe superar los 100 caracteres")));
    }

    @Test
    void testTelefonoObligatorio() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre("Pedro");
        cliente.setEmail("pedro@mail.com");
        cliente.setTelefono(""); //  vacío

        Set<ConstraintViolation<ClienteDTO>> violaciones = validator.validate(cliente);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("El teléfono del cliente es obligatorio")));
    }
}
