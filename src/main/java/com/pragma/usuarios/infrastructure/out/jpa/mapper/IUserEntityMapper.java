package com.pragma.usuarios.infrastructure.out.jpa.mapper;

import com.pragma.usuarios.application.mapper.IRolRequestMapper;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = IRolRequestMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {
    UserEntity toEntity(User user);
    User toUser(UserEntity userEntity);

}
