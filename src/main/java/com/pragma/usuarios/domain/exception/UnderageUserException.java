package com.pragma.usuarios.domain.exception;

public class UnderageUserException extends DomainException{
    public UnderageUserException() {
        super("User must be at least 18 years old");
    }
}
