package com.pragma.usuarios.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDto {

    private Long userId;
    private String email;
    private String rol;
    private String token;
}
