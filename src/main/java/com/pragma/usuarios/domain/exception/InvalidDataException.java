package com.pragma.usuarios.domain.exception;

public class InvalidDataException extends DomainException{
    public InvalidDataException() {
        super("Email or Password incorrect");
    }
}
