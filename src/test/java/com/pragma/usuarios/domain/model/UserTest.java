package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.exception.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User buildValidUser() {
        User user = new User();
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("123456789");
        user.setPhoneNumber("3001234567");
        user.setBirthDate(LocalDate.now().minusYears(25));
        user.setEmail("juan@email.com");
        user.setPassword("password123");
        user.setRol(Rol.PROPIETARIO);
        return user;
    }

    @Test
    void shouldNotThrowExceptionWhenUserIsAdult() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validateIsAdult);
    }

    @Test
    void shouldThrowUnderageUserExceptionWhenUserIsMinor() {
        User user = buildValidUser();
        user.setBirthDate(LocalDate.now().minusYears(16));

        DomainException exception = assertThrows(
                DomainException.class,
                user::validateIsAdult
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User must be at least 18 years old",
                exception.getMessage()
        );
    }


    @Test
    void shouldNotThrowExceptionWhenDocumentNumberIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validateDocumentNumber);
    }

    @Test
    void shouldThrowInvalidDocumentExceptionWhenDocumentIsInvalid() {
        User user = buildValidUser();
        user.setDocumentNumber("ABC123");

        DomainException exception = assertThrows(
                DomainException.class,
                user::validateDocumentNumber
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User document format is invalid",
                exception.getMessage()
        );
    }


    @Test
    void shouldNotThrowExceptionWhenPhoneNumberIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validatePhoneNumber);
    }

    @Test
    void shouldThrowInvalidPhoneNumberExceptionWhenPhoneIsInvalid() {
        User user = buildValidUser();
        user.setPhoneNumber("ABC123");

        DomainException exception = assertThrows(
                DomainException.class,
                user::validatePhoneNumber
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User phone format is invalid",
                exception.getMessage()
        );
    }


    @Test
    void shouldNotThrowExceptionWhenEmailIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validateEmail);
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsInvalid() {
        User user = buildValidUser();
        user.setEmail("invalid-email");

        DomainException exception = assertThrows(
                DomainException.class,
                user::validateEmail
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User email format is invalid",
                exception.getMessage()
        );
    }


    @Test
    void shouldNotThrowExceptionWhenPasswordIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validatePassword);
    }

    @Test
    void shouldThrowWeakPasswordExceptionWhenPasswordIsNull() {
        User user = buildValidUser();
        user.setPassword(null);

        DomainException exception = assertThrows(
                DomainException.class,
                user::validatePassword
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "Password must have at least the minimum required length",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWeakPasswordExceptionWhenPasswordIsTooShort() {
        User user = buildValidUser();
        user.setPassword("123");

        DomainException exception = assertThrows(
                DomainException.class,
                user::validatePassword
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "Password must have at least the minimum required length",
                exception.getMessage()
        );
    }

    @Test
    void shouldNotThrowExceptionWhenEmployeeDataIsValid() {
        User user = buildValidUser();
        assertDoesNotThrow(user::validateEmployee);
    }

        @Test
    void noDeberiaLanzarExcepcionCuandoUserEsMayorDeEdad() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(20));
        user.setRol(Rol.PROPIETARIO);

        assertDoesNotThrow(user::validateIsAdult);
    }

    @Test
    void deberiaLanzarExcepcionCuandoUserEsMenorDeEdad() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(17));
        user.setRol(Rol.PROPIETARIO);

        DomainException exception = assertThrows(
                DomainException.class,
                user::validateIsAdult
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User must be at least 18 years old",
                exception.getMessage()
        );
    }

}
