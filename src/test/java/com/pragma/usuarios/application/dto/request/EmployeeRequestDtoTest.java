package com.pragma.usuarios.application.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeRequestDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private EmployeeRequestDto buildValidDto() {
        EmployeeRequestDto dto = new EmployeeRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("+573001234567");
        dto.setEmail("juan@email.com");
        dto.setPassword("password123");
        return dto;
    }

    @Test
    void shouldPassValidationWhenDtoIsValid() {
        EmployeeRequestDto dto = buildValidDto();

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailWhenFirstNameIsBlank() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setFirstName("");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("El FirstName es obligatorio"));
    }

    @Test
    void shouldFailWhenLastNameIsBlank() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setLastName(" ");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("El apellido es obligatorio"));
    }

    @Test
    void shouldFailWhenDocumentIsNotNumeric() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setDocumentNumber("ABC123");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("El documento debe contener solo números"));
    }

    @Test
    void shouldFailWhenPhoneIsInvalid() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setPhoneNumber("ABC123");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("El celular debe ser numérico y puede iniciar con +"));
    }

    @Test
    void shouldFailWhenPhoneExceedsMaxLength() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setPhoneNumber("+57300123456789"); // > 13

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("El celular no puede tener más de 13 caracteres"));
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setEmail("correo-invalido");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("El email no tiene un formato válido"));
    }

    @Test
    void shouldFailWhenPasswordIsTooShort() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setPassword("123");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("La clave debe tener al menos 8 caracteres"));
    }
}
