package com.pragma.usuarios.domain.exception;

public class InvalidDocumentException extends DomainException{
    public InvalidDocumentException(){
        super("Document format is invalid");
    }
}
