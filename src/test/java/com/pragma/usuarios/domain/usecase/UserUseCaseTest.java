package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.UnderageUserException;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort propietarioPersistencePort;

    private UserUseCase usuarioUseCase;

    @BeforeEach
    void setUp() {
        usuarioUseCase = new UserUseCase(propietarioPersistencePort);
    }

    @Test
    void deberiaAsignarRolPropietarioYGuardarUserCuandoEsMayorDeEdad() {
        // Arrange
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(25));

        // Act
        usuarioUseCase.saveUser(user);

        // Assert
        assertEquals(Rol.PROPIETARIO, user.getRol());
        verify(propietarioPersistencePort, times(1))
                .saveUser(user);
    }

    @Test
    void noDeberiaGuardarUserCuandoEsMenorDeEdad() {
        // Arrange
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(16));

        // Act + Assert
        assertThrows(
                UnderageUserException.class,
                () -> usuarioUseCase.saveUser(user)
        );

        verify(propietarioPersistencePort, never())
                .saveUser(any());
    }
}
