package com.pragma.usuarios.application.mapper;

import com.pragma.usuarios.application.dto.response.UserResponseDto;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IUserResponseMapperTest {

    private final IUserResponseMapper mapper =
            Mappers.getMapper(IUserResponseMapper.class);

    @Test
    void deberiaMapearUserADtoResponseCorrectamente() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("123456789");
        user.setPhoneNumber("3001234567");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setEmail("juan@email.com");
        user.setPassword("password123");
        user.setRol(new Rol(2L));

        UserResponseDto response = mapper.toResponse(user);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getDocumentNumber(), response.getDocumentNumber());
        assertEquals(user.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(user.getBirthDate(), response.getBirthDate());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRol(), response.getRol());
    }
}
