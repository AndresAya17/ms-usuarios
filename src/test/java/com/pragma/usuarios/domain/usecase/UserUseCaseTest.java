package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
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
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPersistencePort passwordEncoderPersistencePort;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoderPersistencePort);
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

        userUseCase.saveUser(user,rol);

        assertEquals(Rol.PROPIETARIO, user.getRol());
        verify(passwordEncoderPersistencePort).encode("password123");
        verify(userPersistencePort).saveUser(user);
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

        DomainException exception = assertThrows(
                DomainException.class,
                () -> userUseCase.saveUser(user, rol)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals(
                "User must be at least 18 years old",
                exception.getMessage()
        );

        verify(userPersistencePort, never()).saveUser(any());
        verify(passwordEncoderPersistencePort, never()).encode(any());
    }

    @Test
    void shouldThrowExceptionWhenRoleIsNotOwner() {
        User employee = new User();
        String rol = Rol.EMPLEADO.name();

        DomainException exception = assertThrows(
                DomainException.class,
                () -> userUseCase.saveEmployee(employee, rol)
        );

        assertEquals(ErrorCode.UNAUTHORIZED, exception.getErrorCode());
        assertEquals("You don't have permissions", exception.getMessage());

        verifyNoInteractions(passwordEncoderPersistencePort);
        verifyNoInteractions(userPersistencePort);
    }

    @Test
    void shouldSaveEmployeeWhenRoleIsOwner() {
        User employee = new User();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDocumentNumber("123456789");
        employee.setPhoneNumber("+573001234567");
        employee.setEmail("john.doe@test.com");
        employee.setBirthDate(LocalDate.of(1995, 1, 1));
        employee.setPassword("plainPassword");

        String rol = Rol.PROPIETARIO.name();
        String encryptedPassword = "encryptedPassword";
        Long expectedId = 1L;

        when(passwordEncoderPersistencePort.encode("plainPassword"))
                .thenReturn(encryptedPassword);

        when(userPersistencePort.saveEmployee(any(User.class)))
                .thenReturn(expectedId);

        Long result = userUseCase.saveEmployee(employee, rol);

        assertEquals(expectedId, result);
        assertEquals(Rol.EMPLEADO, employee.getRol());
        assertEquals(encryptedPassword, employee.getPassword());

        verify(passwordEncoderPersistencePort).encode("plainPassword");
        verify(userPersistencePort).saveEmployee(employee);
    }
}
