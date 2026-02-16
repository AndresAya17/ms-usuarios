package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;


    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public Long saveEmployee(User user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(user));
        return userEntity.getId();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
