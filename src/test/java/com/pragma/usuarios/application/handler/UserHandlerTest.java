package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.EmployeeResponseDto;
import com.pragma.usuarios.application.handler.impl.UserHandler;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {
    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @InjectMocks
    private UserHandler userHandler;

    private UserRequestDto userRequestDto;
    private EmployeeRequestDto employeeRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto();
        employeeRequestDto = new EmployeeRequestDto();
        user = new User();
    }

    @Test
    void deberiaMapearYGuardarUserCorrectamente() {
        UserRequestDto requestDto = new UserRequestDto();
        User user = new User();
        String rol = "OWNER";

        when(userRequestMapper.toUser(requestDto))
                .thenReturn(user);

        userHandler.saveOwner(requestDto, rol);

        // Assert
        verify(userRequestMapper, times(1))
                .toUser(requestDto);

        verify(userServicePort, times(1))
                .saveUser(user, rol);

        verifyNoMoreInteractions(userRequestMapper, userServicePort);
    }

    @Test
    void shouldSaveClientSuccessfully() {
        when(userRequestMapper.toUser(userRequestDto)).thenReturn(user);

        userHandler.saveClient(userRequestDto);

        verify(userRequestMapper, times(1)).toUser(userRequestDto);
        verify(userServicePort, times(1)).saveClient(user);
        verifyNoMoreInteractions(userServicePort, userRequestMapper);
    }

    @Test
    void shouldSaveEmployeeSuccessfully() {
        // arrange
        String rol = "EMPLOYEE";
        Long expectedUserId = 10L;

        employeeRequestDto.setRol(rol);

        when(userRequestMapper.employeeToUser(employeeRequestDto)).thenReturn(user);
        when(userServicePort.saveEmployee(user, rol)).thenReturn(expectedUserId);

        EmployeeResponseDto response = userHandler.saveEmployee(employeeRequestDto);

        assertEquals(expectedUserId, response.getEmployeeUserId());

        verify(userRequestMapper, times(1)).employeeToUser(employeeRequestDto);
        verify(userServicePort, times(1)).saveEmployee(user, rol);
        verifyNoMoreInteractions(userServicePort, userRequestMapper);
    }

}
