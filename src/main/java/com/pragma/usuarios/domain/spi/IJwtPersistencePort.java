package com.pragma.usuarios.domain.spi;

public interface IJwtPersistencePort {
    String generateToken(Long userId, Long roleId);

    boolean validateToken(String token);

    Long getUserId(String token);

    Long getRolId(String token);
}
