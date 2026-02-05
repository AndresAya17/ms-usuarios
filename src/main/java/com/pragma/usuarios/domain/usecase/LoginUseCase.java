package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.application.dto.response.LoginResponseDto;
import com.pragma.usuarios.domain.api.ILoginServicePort;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;

public class LoginUseCase implements ILoginServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPersistencePort passwordEncoderPersistencePort;
    private final IJwtPersistencePort jwtPersistencePort;

    public LoginUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPersistencePort passwordEncoderPersistencePort, IJwtPersistencePort jwtPersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPersistencePort = passwordEncoderPersistencePort;
        this.jwtPersistencePort = jwtPersistencePort;
    }

    @Override
    public LoginResponseDto login(String email, String password) {

        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new DomainException(ErrorCode.DATA_NOT_FOUND, "User not found"));

        if (!passwordEncoderPersistencePort.matches(password, user.getPassword())) {
            throw new DomainException(ErrorCode.INVALID_USER, "Email or Password incorrect");
        }

        String token = jwtPersistencePort.generateToken(
                user.getId(),
                user.getRol().getId()
        );


        return new LoginResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRol().getId().toString(),
                token
        );
    }

}
