package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.domain.usecase.LoginUseCase;
import com.pragma.usuarios.application.dto.response.LoginResponseDto;
import com.pragma.usuarios.domain.exception.InvalidDataException;
import com.pragma.usuarios.domain.exception.UserNotFoundByEmailException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPersistencePort passwordEncoderPersistencePort;

    @Mock
    private IJwtPersistencePort jwtPersistencePort;

    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        loginUseCase = new LoginUseCase(
                userPersistencePort,
                passwordEncoderPersistencePort,
                jwtPersistencePort
        );
    }

    @Test
    void shouldLoginSuccessfullyWhenCredentialsAreValid() {
        // Arrange
        String email = "user@email.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        String token = "jwt-token";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(Rol.ADMINISTRADOR);

        when(userPersistencePort.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPersistencePort.matches(rawPassword, encodedPassword))
                .thenReturn(true);

        when(jwtPersistencePort.generateToken(1L, Rol.ADMINISTRADOR.name()))
                .thenReturn(token);

        // Act
        LoginResponseDto response = loginUseCase.login(email, rawPassword);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals(email, response.getEmail());
        assertEquals(Rol.ADMINISTRADOR.name(), response.getRol());
        assertEquals(token, response.getToken());

        verify(userPersistencePort).findByEmail(email);
        verify(passwordEncoderPersistencePort).matches(rawPassword, encodedPassword);
        verify(jwtPersistencePort).generateToken(1L, Rol.ADMINISTRADOR.name());
        verifyNoMoreInteractions(
                userPersistencePort,
                passwordEncoderPersistencePort,
                jwtPersistencePort
        );
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenEmailDoesNotExist() {
        String email = "notfound@email.com";

        when(userPersistencePort.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundByEmailException.class,
                () -> loginUseCase.login(email, "any-password")
        );

        verify(userPersistencePort).findByEmail(email);
        verifyNoMoreInteractions(userPersistencePort);
        verifyNoInteractions(passwordEncoderPersistencePort, jwtPersistencePort);
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenPasswordIsIncorrect() {
        String email = "user@email.com";
        String rawPassword = "wrongPassword";
        String encodedPassword = "encodedPassword";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(Rol.PROPIETARIO);

        when(userPersistencePort.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPersistencePort.matches(rawPassword, encodedPassword))
                .thenReturn(false);

        assertThrows(
                InvalidDataException.class,
                () -> loginUseCase.login(email, rawPassword)
        );

        verify(userPersistencePort).findByEmail(email);
        verify(passwordEncoderPersistencePort).matches(rawPassword, encodedPassword);
        verifyNoInteractions(jwtPersistencePort);
    }


}
