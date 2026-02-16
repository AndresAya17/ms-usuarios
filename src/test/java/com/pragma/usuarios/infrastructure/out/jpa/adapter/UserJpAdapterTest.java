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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserJpAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @Test
    void deberiaMapearGuardarYRetornarUserCorrectamente() {
        User user = new User();
        UserEntity userEntity = new UserEntity();
        UserEntity userEntityGuardado = new UserEntity();

        when(userEntityMapper.toEntity(user))
                .thenReturn(userEntity);

        when(userRepository.save(userEntity))
                .thenReturn(userEntityGuardado);

        when(userEntityMapper.toUser(userEntityGuardado))
                .thenReturn(user);

        User result = userJpaAdapter.saveUser(user);

        assertSame(user, result);

        verify(userEntityMapper, times(1))
                .toEntity(user);

        verify(userRepository, times(1))
                .save(userEntity);

        verify(userEntityMapper, times(1))
                .toUser(userEntityGuardado);

        verifyNoMoreInteractions(userRepository, userEntityMapper);
    }

    @Test
    void shouldSaveEmployeeAndReturnId() {
        User user = new User();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        Long result = userJpaAdapter.saveEmployee(user);

        assertEquals(1L, result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void shouldFindUserByEmail() {
        String email = "juan@email.com";
        UserEntity userEntity = new UserEntity();
        User user = new User();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(user);

        Optional<User> result = userJpaAdapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        String email = "noexiste@email.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userJpaAdapter.findByEmail(email);

        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail(email);
        verifyNoInteractions(userEntityMapper);
    }

    @Test
    void shouldReturnTrueWhenDocumentNumberExists() {
        String documentNumber = "123456789";

        when(userRepository.existsByDocumentNumber(documentNumber)).thenReturn(true);

        boolean result = userJpaAdapter.existsByDocumentNumber(documentNumber);

        assertTrue(result);
        verify(userRepository).existsByDocumentNumber(documentNumber);
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        String email = "juan@email.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userJpaAdapter.existsByEmail(email);

        assertTrue(result);
        verify(userRepository).existsByEmail(email);
    }
}
