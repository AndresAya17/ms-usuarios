package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.application.dto.response.LoginResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

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
        String email = "user@email.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        String token = "jwt-token";

        Rol rol = new Rol(1L);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(rol);

        when(userPersistencePort.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPersistencePort.matches(rawPassword, encodedPassword))
                .thenReturn(true);

        when(jwtPersistencePort.generateToken(1L, 1L))
                .thenReturn(token);

        LoginResponseDto response = loginUseCase.login(email, rawPassword);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals(email, response.getEmail());
        assertEquals("1", response.getRol());   // 👈 ID COMO STRING
        assertEquals(token, response.getToken());

        verify(userPersistencePort).findByEmail(email);
        verify(passwordEncoderPersistencePort).matches(rawPassword, encodedPassword);
        verify(jwtPersistencePort).generateToken(1L, 1L);
        verifyNoMoreInteractions(
                userPersistencePort,
                passwordEncoderPersistencePort,
                jwtPersistencePort
        );
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenEmailDoesNotExist() {
        String email = "notfound@email.com";
        String password = "any-password";

        when(userPersistencePort.findByEmail(email))
                .thenReturn(Optional.empty());

        DomainException exception = assertThrows(
                DomainException.class,
                () -> loginUseCase.login(email, password)
        );

        assertEquals(ErrorCode.DATA_NOT_FOUND, exception.getErrorCode());
        assertEquals("User not found", exception.getMessage());

        verify(userPersistencePort).findByEmail(email);
        verifyNoMoreInteractions(userPersistencePort);
        verifyNoInteractions(passwordEncoderPersistencePort, jwtPersistencePort);
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenPasswordIsIncorrect() {
        String email = "user@email.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";

        Rol rol = new Rol(1L);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRol(rol);

        when(userPersistencePort.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPersistencePort.matches(rawPassword, encodedPassword))
                .thenReturn(false);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> loginUseCase.login(email, rawPassword)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals("Email or Password incorrect", exception.getMessage());

        verify(userPersistencePort).findByEmail(email);
        verify(passwordEncoderPersistencePort).matches(rawPassword, encodedPassword);

        verifyNoInteractions(jwtPersistencePort);
    }


}
