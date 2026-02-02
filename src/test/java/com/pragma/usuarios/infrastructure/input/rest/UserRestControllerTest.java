package com.pragma.usuarios.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {

    @MockBean
    private IJwtPersistencePort jwtPersistencePort;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserHandler userHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaRetornar201CuandoRequestEsValido() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@email.com");
        dto.setPassword("password123");

        String rol = "ADMINISTRADOR";

        doNothing().when(userHandler).saveOwner(dto, rol);

        mockMvc.perform(
                post("/api/v1/user/owner")
                        .requestAttr("auth.rol", "ADMINISTRADOR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void deberiaRetornar400CuandoRequestEsInvalido() throws Exception {
        UserRequestDto dto = new UserRequestDto();

        mockMvc.perform(
                post("/api/v1/user/owner")
                        .requestAttr("auth.rol", "ADMINISTRADOR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isBadRequest());
    }

}
