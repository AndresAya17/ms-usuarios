package com.pragma.usuarios.domain.exception;

public class InvalidPhoneNumberException extends DomainException{
    public InvalidPhoneNumberException(){
        super("Phone format is invalid");
    }
}
