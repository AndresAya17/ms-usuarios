package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.constants.DomainConstants;
import com.pragma.usuarios.domain.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Rol rol;

    public void validateIsAdult() {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < DomainConstants.MINIMUM_ADULT_AGE) {
            throw new UnderageUserException();
        }
    }
    public void validateDocumentNumber() {
        if (!documentNumber.matches(DomainConstants.DOCUMENT_NUMBER_REGEX)) {
            throw new InvalidDocumentException();
        }
    }

    public void validatePhoneNumber() {
        if (!phoneNumber.matches(DomainConstants.PHONE_NUMBER_REGEX)) {
            throw new InvalidPhoneNumberException();
        }
    }

    public void validateEmail() {
        if (!Pattern.matches(DomainConstants.EMAIL_REGEX, email)) {
            throw new InvalidEmailException();
        }
    }

    public void validatePassword() {
        if (password == null || password.length() < DomainConstants.MIN_PASSWORD_LENGTH) {
            throw new WeakPasswordException();
        }
    }

    public void validateOwner() {
        validateDocumentNumber();
        validatePhoneNumber();
        validateEmail();
        validatePassword();
        validateIsAdult();
    }
    public void validateEmployee() {
        validateDocumentNumber();
        validatePhoneNumber();
        validateEmail();
        validatePassword();
    }

}
