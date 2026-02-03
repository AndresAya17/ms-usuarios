package com.pragma.usuarios.infrastructure.input.rest;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.EmployeeResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "Create a new owner user")
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

    @Operation(
            summary = "Create a new client user",
            description = "Creates a new client user. " +
                    "This endpoint is public and does not require authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client user created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/client")
    public ResponseEntity<Void> saveClient(
            @Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveClient(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create a new employee user",
            description = "Creates a new employee user associated with a restaurant. " +
                    "This operation requires proper authorization."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Employee user created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
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
