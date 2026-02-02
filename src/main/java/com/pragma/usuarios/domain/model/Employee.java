package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import lombok.Setter;

@Setter
public class Employee {

    private Long id;
    private String name;
    private String lastName;
    private String documentNumber;
    private String phone;
    private String email;
    private String password;
    private Rol rol;


    public void validate() {
        validateName();
        validateLastName();
        validateDocumentId();
        validatePhone();
        validateEmail();
        validatePassword();
        validateRole();
    }

    public void validateRole() {
        if (rol == null) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE, "Employee role is not assigned");
        }
    }

    public void validateName() {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE, "First name is required");
        }
    }

    public void validateLastName() {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE, "Last name is required");
        }
    }

    public void validateDocumentId() {
        if (documentNumber == null || !documentNumber.matches("\\d+")) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE,
                    "Identity document must contain only numbers"
            );
        }
    }

    public void validatePhone() {
        if (phone == null || !phone.matches("^\\+?\\d{7,13}$")) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE,
                    "Phone number must be numeric and may start with '+'"
            );
        }
    }

    public void validateEmail() {
        if (email == null || !email.contains("@")) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE, "Email format is not valid");
        }
    }

    public void validatePassword() {
        if (password == null || password.length() < 8) {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE,
                    "Password must contain at least 8 characters"
            );
        }
    }

}
