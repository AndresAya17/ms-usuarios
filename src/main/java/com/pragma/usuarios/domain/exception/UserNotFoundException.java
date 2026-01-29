package com.pragma.usuarios.domain.exception;

public class UserNotFoundException extends DomainException{
    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
    }
}
