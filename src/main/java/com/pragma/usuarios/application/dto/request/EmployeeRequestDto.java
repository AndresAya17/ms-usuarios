package com.pragma.usuarios.application.dto.request;

import com.pragma.usuarios.domain.constants.DomainConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeRequestDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Identity document is required")
    @Pattern(
            regexp = DomainConstants.DOCUMENT_NUMBER_REGEX,
            message = "Identity document must contain only numbers"
    )
    private String documentNumber;

    @NotBlank(message = "Phone number is required")
    @Size(max = 13, message = "Phone number must not exceed 13 characters")
    @Pattern(
            regexp = DomainConstants.PHONE_NUMBER_REGEX,
            message = "Phone number must be numeric and may start with '+'"
    )
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    @NotBlank(message = "Rol is required")
    private String rol;
}
