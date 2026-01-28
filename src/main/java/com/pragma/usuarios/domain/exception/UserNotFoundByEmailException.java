package com.pragma.usuarios.domain.exception;

public class UserNotFoundByEmailException extends RuntimeException{
    public UserNotFoundByEmailException(String email) {
        super("Invalid credentials");
    }
}
