package com.pragma.usuarios.application.mapper;

import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolRequestMapper {
    default RolEntity toEntity(Rol rol) {
        if (rol == null) {
            return null;
        }
        RolEntity entity = new RolEntity();
        entity.setId(rol.getId());
        return entity;
    }

    default Rol toDomain(RolEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Rol(entity.getId());
    }
}
