package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.EmployeeResponseDto;

public interface IUserHandler {
    void saveOwner(UserRequestDto userRequestDto, String rol);
    EmployeeResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto);
}
