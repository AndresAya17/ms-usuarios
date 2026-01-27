package com.pragma.usuarios.infrastructure.out.jpa.security;

import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements IPasswordEncoderPersistencePort {

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
