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

        assertThrows(UnderageUserException.class, user::validateIsAdult);
    }

    // ---------- validateDocumentNumber ----------

    @Test
    void shouldNotThrowExceptionWhenDocumentNumberIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validateDocumentNumber);
    }

    @Test
    void shouldThrowInvalidDocumentExceptionWhenDocumentIsInvalid() {
        User user = buildValidUser();
        user.setDocumentNumber("ABC123");

        assertThrows(InvalidDocumentException.class, user::validateDocumentNumber);
    }

    // ---------- validatePhoneNumber ----------

    @Test
    void shouldNotThrowExceptionWhenPhoneNumberIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validatePhoneNumber);
    }

    @Test
    void shouldThrowInvalidPhoneNumberExceptionWhenPhoneIsInvalid() {
        User user = buildValidUser();
        user.setPhoneNumber("ABC123");

        assertThrows(InvalidPhoneNumberException.class, user::validatePhoneNumber);
    }

    // ---------- validateEmail ----------

    @Test
    void shouldNotThrowExceptionWhenEmailIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validateEmail);
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsInvalid() {
        User user = buildValidUser();
        user.setEmail("invalid-email");

        assertThrows(InvalidEmailException.class, user::validateEmail);
    }

    // ---------- validatePassword ----------

    @Test
    void shouldNotThrowExceptionWhenPasswordIsValid() {
        User user = buildValidUser();

        assertDoesNotThrow(user::validatePassword);
    }

    @Test
    void shouldThrowWeakPasswordExceptionWhenPasswordIsNull() {
        User user = buildValidUser();
        user.setPassword(null);

        assertThrows(WeakPasswordException.class, user::validatePassword);
    }

    @Test
    void shouldThrowWeakPasswordExceptionWhenPasswordIsTooShort() {
        User user = buildValidUser();
        user.setPassword("123");

        assertThrows(WeakPasswordException.class, user::validatePassword);
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

        assertThrows(
                UnderageUserException.class,
                user::validateIsAdult);
    }

}
