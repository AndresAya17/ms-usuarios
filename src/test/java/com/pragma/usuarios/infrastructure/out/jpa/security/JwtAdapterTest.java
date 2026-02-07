package com.pragma.usuarios.infrastructure.out.jpa.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JwtAdapterTest {

    private JwtAdapter jwtAdapter;

    @BeforeEach
    void setUp() {
        jwtAdapter = new JwtAdapter();
    }

    @Test
    void shouldGenerateValidToken() {
        Long userId = 1L;
        Long rolId = 2L;

        String token = jwtAdapter.generateToken(userId, rolId);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldValidateValidToken() {
        String token = jwtAdapter.generateToken(1L, 2L);

        boolean isValid = jwtAdapter.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseWhenTokenIsInvalid() {
        String invalidToken = "this.is.not.a.valid.jwt";

        boolean isValid = jwtAdapter.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void shouldExtractUserIdFromToken() {
        Long expectedUserId = 5L;
        String token = jwtAdapter.generateToken(expectedUserId, 2L);

        Long userId = jwtAdapter.getUserId(token);

        assertEquals(expectedUserId, userId);
    }

    @Test
    void shouldExtractRolFromToken() {
        Long expectedRol = 2L;
        String token = jwtAdapter.generateToken(10L, expectedRol);

        Long rol = jwtAdapter.getRolId(token);

        assertEquals(expectedRol, rol);
    }

}
