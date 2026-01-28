package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.application.dto.response.LoginResponseDto;

public interface ILoginServicePort {

    LoginResponseDto login(String email, String password);
}
