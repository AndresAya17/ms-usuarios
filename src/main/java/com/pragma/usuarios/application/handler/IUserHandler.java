package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.RolUerResponseDto;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);
    RolUerResponseDto getUserRol(Long userId);
}
