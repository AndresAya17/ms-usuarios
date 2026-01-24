package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IUserEntityMapperTest {

    private final IUserEntityMapper mapper =
            Mappers.getMapper(IUserEntityMapper.class);

    @Test
    void deberiaMapearUserADominioAEntityCorrectamente() {
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
        user.setRol(Rol.PROPIETARIO);

        // Act
        UserEntity entity = mapper.toEntity(user);

        // Assert
        assertNotNull(entity);
        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getFirstName(), entity.getFirstName());
        assertEquals(user.getLastName(), entity.getLastName());
        assertEquals(user.getDocumentNumber(), entity.getDocumentNumber());
        assertEquals(user.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(user.getBirthDate(), entity.getBirthDate());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPassword(), entity.getPassword());
        assertEquals(user.getRol(), entity.getRol());
    }

    @Test
    void deberiaMapearUserEntityADominioCorrectamente() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setId(2L);
        entity.setFirstName("Maria");
        entity.setLastName("Lopez");
        entity.setDocumentNumber("987654321");
        entity.setPhoneNumber("3109876543");
        entity.setBirthDate(LocalDate.of(1995, 5, 10));
        entity.setEmail("maria@email.com");
        entity.setPassword("claveSegura");
        entity.setRol(Rol.CLIENTE);

        // Act
        User user = mapper.toUser(entity);

        // Assert
        assertNotNull(user);
        assertEquals(entity.getId(), user.getId());
        assertEquals(entity.getFirstName(), user.getFirstName());
        assertEquals(entity.getLastName(), user.getLastName());
        assertEquals(entity.getDocumentNumber(), user.getDocumentNumber());
        assertEquals(entity.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(entity.getBirthDate(), user.getBirthDate());
        assertEquals(entity.getEmail(), user.getEmail());
        assertEquals(entity.getPassword(), user.getPassword());
        assertEquals(entity.getRol(), user.getRol());
    }

}
