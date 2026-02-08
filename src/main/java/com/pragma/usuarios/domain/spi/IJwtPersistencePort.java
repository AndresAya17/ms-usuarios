package com.pragma.usuarios.domain.spi;

public interface IJwtPersistencePort {
    String generateToken(Long userId, String roleName);

    boolean validateToken(String token);

    Long getUserId(String token);

    String getRol(String token);
}
