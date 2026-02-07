package com.pragma.usuarios.infrastructure.out.jpa.entity;

import com.pragma.usuarios.domain.model.Rol;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class UserEntityTest {

    @Test
    void shouldAssignAndRetrieveAllFields() {
        UserEntity entity = new UserEntity();

        entity.setId(1L);
        entity.setFirstName("Juan");
        entity.setLastName("Perez");
        entity.setDocumentNumber("123456789");
        entity.setPhoneNumber("3001234567");
        entity.setBirthDate(LocalDate.of(1990, 1, 1));
        entity.setEmail("juan@email.com");
        entity.setPassword("password123");

        assertEquals(1L, entity.getId());
        assertEquals("Juan", entity.getFirstName());
        assertEquals("Perez", entity.getLastName());
        assertEquals("123456789", entity.getDocumentNumber());
        assertEquals("3001234567", entity.getPhoneNumber());
        assertEquals(LocalDate.of(1990, 1, 1), entity.getBirthDate());
        assertEquals("juan@email.com", entity.getEmail());
        assertEquals("password123", entity.getPassword());
    }

    @Test
    void shouldWorkWithAllArgsConstructor() {
        RolEntity rolEntity = new RolEntity();
        rolEntity.setId(1L);
        rolEntity.setName("ADMINISTRADOR");
        rolEntity.setDescription("Administrador del sistema");
        UserEntity entity = new UserEntity(
                1L,
                "Juan",
                "Perez",
                "123456789",
                "3001234567",
                LocalDate.of(1990, 1, 1),
                "juan@email.com",
                "password123",
                rolEntity
        );

        assertEquals(1L, entity.getId());
        assertEquals("Juan", entity.getFirstName());
        assertEquals("Perez", entity.getLastName());
        assertEquals("123456789", entity.getDocumentNumber());
        assertEquals("3001234567", entity.getPhoneNumber());
        assertEquals(LocalDate.of(1990, 1, 1), entity.getBirthDate());
        assertEquals("juan@email.com", entity.getEmail());
        assertEquals("password123", entity.getPassword());
    }


}
