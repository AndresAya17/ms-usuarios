package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.RolUserResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.infrastructure.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @Override
    public void saveOwner(UserRequestDto userRequestDto, String rol) {
        User user = userRequestMapper.toUser(userRequestDto);
        userServicePort.saveUser(user, rol);
    }


}
