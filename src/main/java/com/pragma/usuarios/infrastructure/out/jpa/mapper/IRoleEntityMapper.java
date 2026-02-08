package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.infrastructure.out.jpa.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import javax.management.relation.Role;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {
    Role toDomain(RolEntity roleEntity);

    RolEntity toEntity(Role role);
}
