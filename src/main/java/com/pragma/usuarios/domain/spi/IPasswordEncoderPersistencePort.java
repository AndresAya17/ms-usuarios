package com.pragma.usuarios.domain.spi;

public interface IPasswordEncoderPersistencePort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
