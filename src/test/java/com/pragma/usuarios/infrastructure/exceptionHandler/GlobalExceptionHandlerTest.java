package com.pragma.usuarios.infrastructure.exceptionHandler;

import com.pragma.usuarios.domain.exception.UnderageUserException;
import com.pragma.usuarios.domain.exception.UserNotFoundException;
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

public class GlobalExceptionHandlerTest {
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

        @GetMapping("/user-not-found")
        void userNotFound() {
            throw new UserNotFoundException(99L);
        }

        @GetMapping("/underage-user")
        void underageUser() {
            throw new UnderageUserException();
        }
    }

    @Test
    void shouldReturn404WhenUserNotFoundExceptionIsThrown() throws Exception {
        mockMvc.perform(get("/user-not-found")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(containsString("User with id 99 not found")));
    }

    @Test
    void shouldReturn403WhenUnderageUserExceptionIsThrown() throws Exception {
        mockMvc.perform(get("/underage-user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value(containsString("mayor de edad")));
    }
}
