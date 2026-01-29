package com.pragma.usuarios.domain.exception;

public class InvalidEmailException extends DomainException{
    public InvalidEmailException(){
        super("Email format is invalid");
    }
}
