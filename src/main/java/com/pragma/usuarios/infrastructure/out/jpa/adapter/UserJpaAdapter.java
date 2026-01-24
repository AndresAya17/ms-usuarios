package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository usuarioRepository;
    private final IUserEntityMapper usuarioEntityMapper;


    @Override
    public User saveUser(User user) {
        UserEntity userEntity = usuarioRepository.save(usuarioEntityMapper.toEntity(user));
        return usuarioEntityMapper.toUser(userEntity);
    }
}
