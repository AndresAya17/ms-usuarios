package com.pragma.usuarios.domain.exception;

public class UserNotFoundByEmailException extends DomainException{
    public UserNotFoundByEmailException(String email) {
        super("Invalid credentials");
    }
}
