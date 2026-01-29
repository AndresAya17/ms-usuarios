package com.pragma.usuarios.infrastructure.exceptionhandler;

import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.InvalidDataException;
import com.pragma.usuarios.domain.exception.UnderageUserException;
import com.pragma.usuarios.domain.exception.UserNotFoundByEmailException;
import com.pragma.usuarios.infrastructure.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(
            DomainException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundByEmailException ex
    ){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UnderageUserException.class)
    public ResponseEntity<ErrorResponse> handleUnderageUser(
            UnderageUserException ex
    ){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex
    ){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
