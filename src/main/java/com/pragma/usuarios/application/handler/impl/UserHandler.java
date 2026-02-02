package com.pragma.usuarios.application.handler.impl;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.EmployeeResponseDto;
import com.pragma.usuarios.application.handler.IUserHandler;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.User;
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

    @Override
    public void saveClient(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toUser(userRequestDto);
        userServicePort.saveClient(user);
    }

    @Override
    public EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto) {
        String rol = employeeRequestDto.getRol();
        User user = userRequestMapper.employeeToUser(employeeRequestDto);
        Long userId = userServicePort.saveEmployee(user, rol);
        return new EmployeeResponseDto(userId);
    }


}
