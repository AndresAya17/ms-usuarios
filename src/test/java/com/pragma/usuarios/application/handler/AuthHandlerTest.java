package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.LoginRequestDto;
import com.pragma.usuarios.application.dto.response.LoginResponseDto;
import com.pragma.usuarios.application.handler.impl.AuthHandler;
import com.pragma.usuarios.domain.api.ILoginServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private ILoginServicePort loginServicePort;

    @InjectMocks
    private AuthHandler authHandler;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldLoginSuccessfully() {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail("user@email.com");
        requestDto.setPassword("password123");

        LoginResponseDto expectedResponse = new LoginResponseDto(
                1L,
                "user@email.com",
                "ADMINISTRATOR",
                "jwt-token"

        );

        when(loginServicePort.login(
                "user@email.com",
                "password123"
        )).thenReturn(expectedResponse);

        LoginResponseDto response = authHandler.login(requestDto);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(expectedResponse);

        verify(loginServicePort, times(1))
                .login("user@email.com", "password123");

        verifyNoMoreInteractions(loginServicePort);
    }

}
