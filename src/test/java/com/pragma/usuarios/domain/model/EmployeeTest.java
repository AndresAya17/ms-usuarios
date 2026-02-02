package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee buildValidEmployee() {
        Employee employee = new Employee();
        employee.setName("Juan");
        employee.setLastName("Perez");
        employee.setDocumentNumber("123456789");
        employee.setPhone("+573001234567");
        employee.setEmail("juan@mail.com");
        employee.setPassword("password123");
        employee.setRol(Rol.EMPLEADO);
        return employee;
    }

    @Test
    void shouldNotThrowWhenEmployeeIsValid() {
        Employee employee = buildValidEmployee();
        assertDoesNotThrow(employee::validate);
    }


    @Test
    void shouldThrowExceptionWhenRoleIsNull() {
        Employee employee = buildValidEmployee();
        employee.setRol(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateRole
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals("Employee role is not assigned", ex.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Employee employee = buildValidEmployee();
        employee.setName(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateName
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals("First name is required", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Employee employee = buildValidEmployee();
        employee.setName("   ");

        assertThrows(DomainException.class, employee::validateName);
    }


    @Test
    void shouldThrowExceptionWhenLastNameIsNull() {
        Employee employee = buildValidEmployee();
        employee.setLastName(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateLastName
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals("Last name is required", ex.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenDocumentIsNull() {
        Employee employee = buildValidEmployee();
        employee.setDocumentNumber(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateDocumentId
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenDocumentIsNotNumeric() {
        Employee employee = buildValidEmployee();
        employee.setDocumentNumber("ABC123");

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateDocumentId
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals(
                "Identity document must contain only numbers",
                ex.getMessage()
        );
    }


    @Test
    void shouldThrowExceptionWhenPhoneIsNull() {
        Employee employee = buildValidEmployee();
        employee.setPhone(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validatePhone
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsInvalid() {
        Employee employee = buildValidEmployee();
        employee.setPhone("ABC123");

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validatePhone
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals(
                "Phone number must be numeric and may start with '+'",
                ex.getMessage()
        );
    }


    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        Employee employee = buildValidEmployee();
        employee.setEmail(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateEmail
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        Employee employee = buildValidEmployee();
        employee.setEmail("invalid-email");

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validateEmail
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals("Email format is not valid", ex.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        Employee employee = buildValidEmployee();
        employee.setPassword(null);

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validatePassword
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        Employee employee = buildValidEmployee();
        employee.setPassword("123");

        DomainException ex = assertThrows(
                DomainException.class,
                employee::validatePassword
        );

        assertEquals(ErrorCode.INVALID_EMPLOYEE, ex.getErrorCode());
        assertEquals(
                "Password must contain at least 8 characters",
                ex.getMessage()
        );
    }


}
