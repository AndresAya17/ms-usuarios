package com.pragma.usuarios.application.dto.request;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
class LoginRequestDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private LoginRequestDto buildValidDto() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("user@email.com");
        dto.setPassword("password123");
        return dto;
    }

    @Test
    void shouldPassValidationWhenDtoIsValid() {
        LoginRequestDto dto = buildValidDto();

        Set<ConstraintViolation<LoginRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        LoginRequestDto dto = buildValidDto();
        dto.setEmail("");

        Set<ConstraintViolation<LoginRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        LoginRequestDto dto = buildValidDto();
        dto.setEmail("invalid-email");

        Set<ConstraintViolation<LoginRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        LoginRequestDto dto = buildValidDto();
        dto.setPassword(" ");

        Set<ConstraintViolation<LoginRequestDto>> violations =
                validator.validate(dto);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }
}
