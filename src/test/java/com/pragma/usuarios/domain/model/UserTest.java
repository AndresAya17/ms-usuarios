package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.exception.*;
import com.pragma.usuarios.domain.service.UserDomainValidator;
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
        user.setRol(new Rol(1L));
        return user;
    }

    @Test
    void shouldNotThrowExceptionWhenUserIsAdult() {
        User user = buildValidUser();

        assertDoesNotThrow(() ->
                UserDomainValidator.validateOwner(user)
        );
    }

    @Test
    void shouldThrowUnderageUserExceptionWhenUserIsMinor() {
        User user = buildValidUser();
        user.setBirthDate(LocalDate.now().minusYears(16));

        DomainException exception = assertThrows(
                DomainException.class,
                () -> UserDomainValidator.validateOwner(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User must be at least 18 years old",
                exception.getMessage()
        );
    }

    @Test
    void shouldNotThrowExceptionWhenEmployeeDataIsValid() {
        User user = buildValidUser();
        assertDoesNotThrow(() ->
                UserDomainValidator.validateUser(user)
        );
    }



}
