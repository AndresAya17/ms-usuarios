package com.pragma.usuarios.infrastructure.out.jpa.adapter;

import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserJpAdapterTest {

    @Mock
    private IUserRepository usuarioRepository;

    @Mock
    private IUserEntityMapper usuarioEntityMapper;

    @InjectMocks
    private UserJpaAdapter usuarioJpaAdapter;

    @Test
    void deberiaMapearGuardarYRetornarUserCorrectamente() {
        // Arrange
        User user = new User();
        UserEntity userEntity = new UserEntity();
        UserEntity userEntityGuardado = new UserEntity();

        when(usuarioEntityMapper.toEntity(user))
                .thenReturn(userEntity);

        when(usuarioRepository.save(userEntity))
                .thenReturn(userEntityGuardado);

        when(usuarioEntityMapper.toUser(userEntityGuardado))
                .thenReturn(user);

        // Act
        User resultado = usuarioJpaAdapter.saveUser(user);

        // Assert
        assertSame(user, resultado);

        verify(usuarioEntityMapper, times(1))
                .toEntity(user);

        verify(usuarioRepository, times(1))
                .save(userEntity);

        verify(usuarioEntityMapper, times(1))
                .toUser(userEntityGuardado);

        verifyNoMoreInteractions(usuarioRepository, usuarioEntityMapper);
    }
}
