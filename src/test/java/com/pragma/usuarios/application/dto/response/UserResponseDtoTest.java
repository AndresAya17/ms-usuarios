package com.pragma.usuarios.application.dto.response;

import com.pragma.usuarios.domain.model.Rol;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class UserResponseDtoTest {

    @Test
    void deberiaPermitirAsignarYObtenerValores() {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(1L);
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@email.com");
        dto.setPassword("password123");
        dto.setRol(Rol.ADMINISTRADOR);

        assertEquals(1L, dto.getId());
        assertEquals("Juan", dto.getFirstName());
        assertEquals("Perez", dto.getLastName());
        assertEquals("123456789", dto.getDocumentNumber());
        assertEquals("3001234567", dto.getPhoneNumber());
        assertEquals(LocalDate.of(1990, 1, 1), dto.getBirthDate());
        assertEquals("juan@email.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals(Rol.ADMINISTRADOR, dto.getRol());
    }

}
