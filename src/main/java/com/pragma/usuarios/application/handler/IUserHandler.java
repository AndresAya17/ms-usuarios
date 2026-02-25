package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.ClientPhoneResponse;

public interface IUserHandler {
    void saveOwner(UserRequestDto userRequestDto);
    void saveClient(UserRequestDto userRequestDto);
    void saveEmployee(EmployeeRequestDto employeeRequestDto, Long restaurantId, Long userId);
    ClientPhoneResponse getPhoneClient(Long userId);
}
