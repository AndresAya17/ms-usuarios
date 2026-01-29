package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.UnderageUserException;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
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

    @Mock
    private IPasswordEncoderPersistencePort passwordEncoderPersistencePort;

    private UserUseCase usuarioUseCase;

    @BeforeEach
    void setUp() {
        usuarioUseCase = new UserUseCase(propietarioPersistencePort, passwordEncoderPersistencePort);
    }

    @Test
    void deberiaAsignarRolPropietarioYGuardarUserCuandoEsMayorDeEdad() {
        User user = new User();
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("123456789");
        user.setPhoneNumber("3001234567");
        user.setEmail("juan@email.com");
        user.setPassword("password123");
        user.setBirthDate(LocalDate.now().minusYears(25));

        String rol = Rol.ADMINISTRADOR.name();
        when(passwordEncoderPersistencePort.encode("password123"))
                .thenReturn("encryptedPassword");

        usuarioUseCase.saveUser(user,rol);

        assertEquals(Rol.PROPIETARIO, user.getRol());
        verify(passwordEncoderPersistencePort).encode("password123");
        verify(propietarioPersistencePort).saveUser(user);
    }

    @Test
    void noDeberiaGuardarUserCuandoEsMenorDeEdad() {
        User user = new User();
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("123456789");
        user.setPhoneNumber("3001234567");
        user.setEmail("juan@email.com");
        user.setPassword("password123");
        user.setBirthDate(LocalDate.now().minusYears(16));
        String rol = Rol.ADMINISTRADOR.name();

        assertThrows(
                UnderageUserException.class,
                () -> usuarioUseCase.saveUser(user, rol)
        );

        verify(propietarioPersistencePort, never()).saveUser(any());
        verify(passwordEncoderPersistencePort, never()).encode(any());
    }
}
