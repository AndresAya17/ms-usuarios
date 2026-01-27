package com.pragma.usuarios.infrastructure.exceptionHandler;

import com.pragma.usuarios.infrastructure.exceptionhandler.ErrorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class ErrorResponseTest {

    @Test
    void shouldCreateErrorResponseWithMessage() {
        // arrange
        String expectedMessage = "User not found";

        // act
        ErrorResponse errorResponse = new ErrorResponse(expectedMessage);

        // assert
        assertNotNull(errorResponse);
        assertEquals(expectedMessage, errorResponse.getMessage());
    }
}
