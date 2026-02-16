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
        String roleName = "OWNER";

        String token = jwtAdapter.generateToken(userId, roleName);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldValidateValidToken() {
        Long userId = 1L;
        String roleName = "OWNER";

        String token = jwtAdapter.generateToken(userId, roleName);

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
        String roleName = "OWNER";

        String token = jwtAdapter.generateToken(expectedUserId, roleName);

        Long userId = jwtAdapter.getUserId(token);

        assertEquals(expectedUserId, userId);
    }

    @Test
    void shouldExtractRolFromToken() {
        String expectedRole = "OWNER";

        String token = jwtAdapter.generateToken(10L, expectedRole);

        String role = jwtAdapter.getRol(token);

        assertEquals(expectedRole, role);
    }

}
