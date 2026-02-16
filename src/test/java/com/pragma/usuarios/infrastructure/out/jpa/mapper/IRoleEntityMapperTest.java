package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RolEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class IRoleEntityMapperTest {

    private final IRoleEntityMapper mapper =
            Mappers.getMapper(IRoleEntityMapper.class);

    @Test
    void shouldMapRolToRolEntityCorrectly() {

        Rol rol = new Rol();
        rol.setId(2L);
        rol.setName("OWNER");

        RolEntity entity = mapper.toEntity(rol);

        assertNotNull(entity);
        assertEquals(rol.getId(), entity.getId());
        assertEquals(rol.getName(), entity.getName());
    }

    @Test
    void shouldMapRolEntityToRolCorrectly() {

        RolEntity entity = new RolEntity();
        entity.setId(3L);
        entity.setName("ADMIN");

        Rol rol = mapper.toDomain(entity);

        assertNotNull(rol);
        assertEquals(entity.getId(), rol.getId());
        assertEquals(entity.getName(), rol.getName());
    }
}
