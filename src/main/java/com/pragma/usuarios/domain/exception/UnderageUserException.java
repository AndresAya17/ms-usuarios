package com.pragma.usuarios.domain.exception;

public class UnderageUserException extends RuntimeException{
    public UnderageUserException() {
        super("El usuario debe ser mayor de edad");
    }
}
