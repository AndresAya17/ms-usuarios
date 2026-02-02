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
        dto.setRol("Propietario");
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
                .anyMatch(v -> v.getMessage().equals("First name is required"));
    }

    @Test
    void shouldFailWhenLastNameIsBlank() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setLastName(" ");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("Last name is required"));
    }

    @Test
    void shouldFailWhenDocumentIsNotNumeric() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setDocumentNumber("ABC123");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v ->
                        v.getMessage().equals("Identity document must contain only numbers")
                );
    }

    @Test
    void shouldFailWhenPhoneIsNotNumeric() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setPhoneNumber("ABC123");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v ->
                        v.getMessage().equals("Phone number must be numeric and may start with '+'")
                );
    }

    @Test
    void shouldFailWhenPhoneExceedsMaxLength() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setPhoneNumber("+5730012345678"); // > 13

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v ->
                        v.getMessage().equals("Phone number must not exceed 13 characters")
                );
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setEmail("correo-invalido");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v ->
                        v.getMessage().equals("Email format is not valid")
                );
    }

    @Test
    void shouldFailWhenPasswordIsTooShort() {
        EmployeeRequestDto dto = buildValidDto();
        dto.setPassword("123");

        Set<ConstraintViolation<EmployeeRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v ->
                        v.getMessage().equals("Password must contain at least 8 characters")
                );
    }

}
