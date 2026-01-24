package com.pragma.usuarios.application.dto.response;

import com.pragma.usuarios.domain.model.Rol;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String DocumentNumber;
    private String PhoneNumber;
    private LocalDate BirthDate;
    private String email;
    private String password;
    private Rol rol;
}
