package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.RolUserResponseDto;

public interface IUserHandler {
    void saveOwner(UserRequestDto userRequestDto);
    RolUserResponseDto getUserRol(Long userId);
}
