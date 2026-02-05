package com.pragma.usuarios.infrastructure.out.jpa.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BCryptPasswordEncoderAdapterTest {

    private BCryptPasswordEncoderAdapter passwordEncoderAdapter;

    @BeforeEach
    void setUp() {
        passwordEncoderAdapter = new BCryptPasswordEncoderAdapter();
    }

    @Test
    void shouldEncodePassword() {
        String rawPassword = "password123";

        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    @Test
    void shouldMatchPasswordSuccessfully() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);

        // Act
        boolean matches = passwordEncoderAdapter.matches(rawPassword, encodedPassword);

        // Assert
        assertTrue(matches);
    }

    @Test
    void shouldNotMatchWhenPasswordIsIncorrect() {
        // Arrange
        String rawPassword = "password123";
        String wrongPassword = "wrongPassword";
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);

        // Act
        boolean matches = passwordEncoderAdapter.matches(wrongPassword, encodedPassword);

        // Assert
        assertFalse(matches);
    }

}
