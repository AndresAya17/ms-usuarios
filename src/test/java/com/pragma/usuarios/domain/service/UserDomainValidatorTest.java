package com.pragma.usuarios.domain.service;

import com.pragma.usuarios.domain.constants.DomainConstants;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.pragma.usuarios.domain.exception.ErrorCode;

import java.time.LocalDate;

class UserDomainValidatorTest {

    private User buildValidUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("123456789");
        user.setPhoneNumber("3001234567");
        user.setBirthDate(LocalDate.now().minusYears(25));
        user.setEmail("juan@email.com");
        user.setPassword("Password123");
        user.setRol(new Rol(1L));
        return user;
    }

    @Test
    void shouldNotThrowExceptionWhenUserIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(() ->
                UserDomainValidator.validateUser(user)
        );
    }

    @Test
    void shouldThrowExceptionWhenDocumentIsInvalid() {
        User user = buildValidUser();
        user.setDocumentNumber("ABC123");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateUser(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsInvalid() {
        User user = buildValidUser();
        user.setPhoneNumber("123");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateUser(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        User user = buildValidUser();
        user.setEmail("correo-invalido");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateUser(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        User user = buildValidUser();
        user.setPassword("123");

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateUser(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
    }

    @Test
    void shouldNotThrowExceptionWhenOwnerIsAdult() {
        User user = buildValidUser();
        user.setBirthDate(LocalDate.now().minusYears(20));

        assertDoesNotThrow(() ->
                UserDomainValidator.validateOwner(user)
        );
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsNull() {
        User user = buildValidUser();
        user.setBirthDate(null);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateOwner(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals("Birth date is required", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderAge() {
        User user = buildValidUser();
        user.setBirthDate(LocalDate.now().minusYears(
                DomainConstants.MINIMUM_ADULT_AGE - 1
        ));

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateOwner(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals("User must be at least 18 years old", exception.getMessage());
    }
}
