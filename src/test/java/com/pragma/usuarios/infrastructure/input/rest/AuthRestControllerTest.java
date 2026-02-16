package com.pragma.usuarios.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.usuarios.application.dto.request.LoginRequestDto;
import com.pragma.usuarios.application.dto.response.LoginResponseDto;
import com.pragma.usuarios.application.handler.IAuthHandler;
import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import com.pragma.usuarios.infrastructure.input.security.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IAuthHandler authHandler;

    @MockBean
    private IJwtPersistencePort jwtPersistencePort;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    @WithMockUser
    void shouldReturn200WhenLoginIsValid() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail("juan@email.com");
        requestDto.setPassword("password123");

        LoginResponseDto responseDto = new LoginResponseDto(
                1L,
                "juan@email.com",
                "ROLE_2",
                "fake-jwt-token"
        );

        when(authHandler.login(any(LoginRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.email").value("juan@email.com"))
                .andExpect(jsonPath("$.rol").value("ROLE_2"))
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));

        verify(authHandler).login(any(LoginRequestDto.class));
    }
}
