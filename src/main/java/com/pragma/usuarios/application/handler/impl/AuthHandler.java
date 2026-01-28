package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.LoginRequestDto;
import com.pragma.usuarios.application.dto.response.LoginResponseDto;
import com.pragma.usuarios.application.handler.IAuthHandler;

import com.pragma.usuarios.domain.api.ILoginServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class AuthHandler implements IAuthHandler {

    private final ILoginServicePort loginServicePort;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return loginServicePort.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );
    }
}
