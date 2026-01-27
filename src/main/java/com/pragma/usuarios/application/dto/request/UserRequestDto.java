package com.pragma.usuarios.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "El FirstName es obligatorio")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @NotBlank(message = "El documento es obligatorio")
    @Pattern(
            regexp = "\\d+",
            message = "El documento debe contener solo números"
    )
    private String documentNumber;

    @NotBlank(message = "El celular es obligatorio")
    @Size(max = 13, message = "El celular no puede tener más de 13 caracteres")
    @Pattern(
            regexp = "^\\+?\\d{7,13}$",
            message = "El celular debe ser numérico y puede iniciar con +"
    )
    private String phoneNumber;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate birthDate;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 8, message = "La clave debe tener al menos 8 caracteres")
    private String password;
}
