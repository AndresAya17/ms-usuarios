package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.EmployeeResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Add a new usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/owner")
    public ResponseEntity<Void> saveOwner(
            @RequestAttribute("auth.rol") String rol,
            @Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveOwner(userRequestDto, rol);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/client")
    public ResponseEntity<Void> saveClient(
            @Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveClient(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeResponseDto> saveEmployee(
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto response =
                userHandler.saveEmployee(employeeRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
