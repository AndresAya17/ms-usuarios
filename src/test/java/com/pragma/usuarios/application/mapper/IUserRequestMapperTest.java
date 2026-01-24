package com.pragma.usuarios.application.mapper;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IUserRequestMapperTest {

    private final IUserRequestMapper mapper =
            Mappers.getMapper(IUserRequestMapper.class);

    @Test
    void deberiaMapearUserRequestDtoADominioCorrectamente() {
        // Arrange
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName("Juan");
        dto.setLastName("Perez");
        dto.setDocumentNumber("123456789");
        dto.setPhoneNumber("3001234567");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("juan@email.com");
        dto.setPassword("password123");

        // Act
        User user = mapper.toUser(dto);

        // Assert
        assertNotNull(user);
        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getDocumentNumber(), user.getDocumentNumber());
        assertEquals(dto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(dto.getBirthDate(), user.getBirthDate());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPassword(), user.getPassword());

        // El rol NO lo asigna el mapper (lo hace el UseCase)
        assertNull(user.getRol());
    }
}
