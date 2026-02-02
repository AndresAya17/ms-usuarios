package com.pragma.usuarios.infrastructure.exceptionHandler;

import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.infrastructure.exceptionhandler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @RestController
    static class TestController {

        @GetMapping("/data-not-found")
        void dataNotFound() {
            throw new DomainException(ErrorCode.DATA_NOT_FOUND, "User not found");
        }

        @GetMapping("/unauthorized")
        void unauthorized() {
            throw new DomainException(ErrorCode.UNAUTHORIZED, "Invalid credentials");
        }

        @GetMapping("/forbidden")
        void forbidden() {
            throw new DomainException(ErrorCode.FORBIDDEN, "Access denied");
        }

        @GetMapping("/invalid-user")
        void invalidUser() {
            throw new DomainException(ErrorCode.INVALID_USER, "Invalid user data");
        }

        @GetMapping("/invalid-employee")
        void invalidEmployee() {
            throw new DomainException(ErrorCode.INVALID_EMPLOYEE, "Invalid employee data");
        }
    }

    @Test
    void shouldReturn404WhenDataNotFound() throws Exception {
        mockMvc.perform(get("/data-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("DATA_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturn401WhenUnauthorized() throws Exception {
        mockMvc.perform(get("/unauthorized"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    void shouldReturn403WhenForbidden() throws Exception {
        mockMvc.perform(get("/forbidden"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode").value("FORBIDDEN"))
                .andExpect(jsonPath("$.message").value("Access denied"));
    }
}
