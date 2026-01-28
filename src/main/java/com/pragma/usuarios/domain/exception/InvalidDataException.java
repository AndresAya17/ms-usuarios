package com.pragma.usuarios.domain.exception;

public class InvalidDataException extends RuntimeException{
    public InvalidDataException() {
        super("Email or Password incorrect");
    }
}
