package com.pragma.usuarios.domain.exception;

public class WeakPasswordException extends DomainException{
    public WeakPasswordException(){
        super("Password must have at least the minimum required length");
    }
}
