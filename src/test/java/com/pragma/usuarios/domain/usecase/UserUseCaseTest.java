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

    private User user;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("plainPassword123");

        userUseCase = new UserUseCase(
                userPersistencePort,
                passwordEncoderPersistencePort
        );
    }

    private User buildValidClientUser() {
        User user = new User();
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setDocumentNumber("123456789");
        user.setPhoneNumber("3001234567");
        user.setEmail("juan@email.com");
        user.setPassword("plainPassword123");
        user.setBirthDate(LocalDate.now().minusYears(20));
        return user;
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

        when(passwordEncoderPersistencePort.encode("password123"))
                .thenReturn("encryptedPassword");

        userUseCase.saveUser(user);

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

        DomainException exception = assertThrows(
                DomainException.class,
                () -> userUseCase.saveUser(user)
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
    void shouldSaveEmployeeWhenRoleIsOwner() {
        User employee = new User();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDocumentNumber("123456789");
        employee.setPhoneNumber("+573001234567");
        employee.setEmail("john.doe@test.com");
        employee.setBirthDate(LocalDate.of(1995, 1, 1));
        employee.setPassword("plainPassword");

        String encryptedPassword = "encryptedPassword";
        Long expectedId = 1L;

        when(passwordEncoderPersistencePort.encode("plainPassword"))
                .thenReturn(encryptedPassword);

        when(userPersistencePort.saveEmployee(any(User.class)))
                .thenReturn(expectedId);

        Long result = userUseCase.saveEmployee(employee);

        assertEquals(expectedId, result);
        assertEquals(encryptedPassword, employee.getPassword());

        verify(passwordEncoderPersistencePort).encode("plainPassword");
        verify(userPersistencePort).saveEmployee(employee);
    }

    @Test
    void shouldSaveClientWithEncryptedPasswordAndClientRole() {
        User user = buildValidClientUser();

        String encryptedPassword = "encryptedPassword123";

        when(passwordEncoderPersistencePort.encode("plainPassword123"))
                .thenReturn(encryptedPassword);

        when(userPersistencePort.existsByEmail(user.getEmail()))
                .thenReturn(false);

        when(userPersistencePort.existsByDocumentNumber(user.getDocumentNumber()))
                .thenReturn(false);

        userUseCase.saveClient(user);

        assertEquals(encryptedPassword, user.getPassword());

        verify(userPersistencePort).existsByEmail(user.getEmail());
        verify(userPersistencePort).existsByDocumentNumber(user.getDocumentNumber());
        verify(passwordEncoderPersistencePort).encode("plainPassword123");
        verify(userPersistencePort).saveUser(user);
    }
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = buildValidClientUser();
        when(userPersistencePort.existsByEmail(user.getEmail()))
                .thenReturn(true);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> userUseCase.saveEmployee(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals("Email already exists", exception.getMessage());

        verify(userPersistencePort).existsByEmail(user.getEmail());
        verifyNoMoreInteractions(userPersistencePort);
    }

    @Test
    void shouldThrowExceptionWhenDocumentNumberAlreadyExists() {
        User user = buildValidClientUser();

        when(userPersistencePort.existsByEmail(user.getEmail()))
                .thenReturn(false);

        when(userPersistencePort.existsByDocumentNumber(user.getDocumentNumber()))
                .thenReturn(true);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> userUseCase.saveEmployee(user)
        );

        assertEquals(ErrorCode.INVALID_USER, exception.getErrorCode());
        assertEquals("Document number already exists", exception.getMessage());

        verify(userPersistencePort).existsByEmail(user.getEmail());
        verify(userPersistencePort).existsByDocumentNumber(user.getDocumentNumber());
    }
}
