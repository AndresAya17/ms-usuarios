package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.infrastructure.out.jpa.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {
    Rol toDomain(RolEntity roleEntity);
    RolEntity toEntity(Rol rol);
}
