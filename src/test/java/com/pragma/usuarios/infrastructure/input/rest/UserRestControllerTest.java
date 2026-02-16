package com.pragma.usuarios.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
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
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

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


        doNothing().when(userHandler).saveOwner(dto);

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

    @Test
    void shouldReturn201WhenCreateClientIsValid() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@email.com");
        dto.setPassword("password123");
        doNothing().when(userHandler).saveClient(any(UserRequestDto.class));

        mockMvc.perform(
                        post("/api/v1/user/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated());
        verify(userHandler).saveClient(any(UserRequestDto.class));
    }

    @Test
    void shouldReturn400WhenCreateClientRequestIsInvalid() throws Exception {
        UserRequestDto dto = new UserRequestDto();

        mockMvc.perform(
                        post("/api/v1/user/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userHandler);
    }


    @Test
    @WithMockUser(authorities = "OWNER")
    void shouldReturn201WhenCreateEmployeeIsValidAndAuthorized() throws Exception {
        Long restaurantId = 1L;
        Long userId = 99L;

        EmployeeRequestDto dto = new EmployeeRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("3001234567");
        dto.setEmail("juan@email.com");
        dto.setPassword("password123");

        doNothing().when(userHandler)
                .saveEmployee(any(EmployeeRequestDto.class), eq(restaurantId), anyLong());

        mockMvc.perform(
                        post("/api/v1/user/employee/restaurant/{id}", restaurantId)
                                .requestAttr("auth.userId", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated());

        verify(userHandler)
                .saveEmployee(any(EmployeeRequestDto.class), eq(restaurantId), eq(userId));
    }

    @Test
    void shouldReturn400WhenCreateEmployeeRequestIsInvalid() throws Exception {
        Long restaurantId = 1L;
        Long userId = 99L;

        EmployeeRequestDto dto = new EmployeeRequestDto();

        mockMvc.perform(
                        post("/api/v1/user/employee/restaurant/{id}", restaurantId)
                                .requestAttr("auth.userId", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userHandler);
    }

}
