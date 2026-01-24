package com.pragma.usuarios.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deberiaSerValidoCuandoTodosLosCamposSonCorrectos() {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("+573001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan.perez@email.com");
        dto.setPassword("password123");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deberiaFallarCuandoFirstNameEsVacio() {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("correo@email.com");
        dto.setPassword("password123");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deberiaFallarCuandoEmailNoEsValido() {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("correo-invalido");
        dto.setPassword("password123");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deberiaFallarCuandoPhoneNumberTieneFormatoInvalido() {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456");
        dto.setPhoneNumber("ABC123");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("correo@email.com");
        dto.setPassword("password123");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deberiaFallarCuandoBirthDateEsNull() {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(null);
        dto.setEmail("correo@email.com");
        dto.setPassword("password123");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertFalse(violations.isEmpty());
    }


}
