package com.pragma.usuarios.infrastructure.exceptionHandler;

import com.pragma.usuarios.infrastructure.exceptionhandler.ErrorResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class ErrorResponseTest {

    @Test
    void shouldCreateErrorResponseWithMessage() {
        String expectedErrorCode = "INVALID_DISH";
        String expectedMessage = "Invalid request";

        ErrorResponse errorResponse =
                new ErrorResponse(expectedErrorCode, expectedMessage);

        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getErrorCode()).isEqualTo(expectedErrorCode);
        assertThat(errorResponse.getMessage()).isEqualTo(expectedMessage);
    }
}
