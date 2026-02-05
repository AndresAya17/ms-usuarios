package com.pragma.usuarios.domain.service;

import com.pragma.usuarios.domain.constants.DomainConstants;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.domain.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserDomainValidator {

    private UserDomainValidator() {}

    public static void validateOwner(User user) {
        validateCommon(user);
        validateIsAdult(user);
    }

    public static void validateUser(User user) {
        validateCommon(user);
    }

    private static void validateCommon(User user) {
        validateDocumentNumber(user.getDocumentNumber());
        validatePhoneNumber(user.getPhoneNumber());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private static void validateIsAdult(User user) {
        if (user.getBirthDate() == null) {
            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "Birth date is required"
            );
        }

        int age = Period
                .between(user.getBirthDate(), LocalDate.now())
                .getYears();

        if (age < DomainConstants.MINIMUM_ADULT_AGE) {
            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "User must be at least 18 years old"
            );
        }
    }

    private static void validateDocumentNumber(String documentNumber) {
        if (documentNumber == null ||
                !documentNumber.matches(DomainConstants.DOCUMENT_NUMBER_REGEX)) {

            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "User document format is invalid"
            );
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null ||
                !phoneNumber.matches(DomainConstants.PHONE_NUMBER_REGEX)) {

            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "User phone format is invalid"
            );
        }
    }

    private static void validateEmail(String email) {
        if (email == null ||
                !Pattern.matches(DomainConstants.EMAIL_REGEX, email)) {

            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "User email format is invalid"
            );
        }
    }

    private static void validatePassword(String password) {
        if (password == null ||
                password.length() < DomainConstants.MIN_PASSWORD_LENGTH) {

            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "Password must have at least the minimum required length"
            );
        }
    }
}
