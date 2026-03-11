package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.EmployeeRequestDto;
import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.dto.response.ClientPhoneResponse;
import com.pragma.usuarios.application.dto.response.EmployeeEmailResponseDto;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        when(userRequestMapper.toUser(requestDto))
                .thenReturn(user);

        userHandler.saveOwner(requestDto);

        verify(userRequestMapper, times(1))
                .toUser(requestDto);

        verify(userServicePort, times(1))
                .saveUser(user);

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
        Long restaurantId = 1L;
        Long userId = 5L;

        when(userRequestMapper.employeeToUser(employeeRequestDto)).thenReturn(user);

        doNothing().when(userServicePort)
                .saveEmployee(user, restaurantId, userId);

        userHandler.saveEmployee(employeeRequestDto, restaurantId, userId);

        verify(userRequestMapper, times(1))
                .employeeToUser(employeeRequestDto);

        verify(userServicePort, times(1))
                .saveEmployee(user, restaurantId, userId);

        verifyNoMoreInteractions(userServicePort, userRequestMapper);
    }

    @Test
    void shouldReturnClientPhoneResponseSuccessfully() {
        Long userId = 1L;
        String phone = "3001234567";

        when(userServicePort.getPhone(userId)).thenReturn(phone);

        ClientPhoneResponse result = userHandler.getPhoneClient(userId);

        assertNotNull(result);
        assertEquals(phone, result.getPhoneNumber());

        verify(userServicePort).getPhone(userId);
    }

    @Test
    void shouldReturnEmployeeEmailResponseSuccessfully() {
        Long userId = 2L;
        String email = "empleado@test.com";

        when(userServicePort.getEmail(userId)).thenReturn(email);

        EmployeeEmailResponseDto result = userHandler.getEmailEmployee(userId);

        assertNotNull(result);
        assertEquals(email, result.getEmail());

        verify(userServicePort).getEmail(userId);
    }

}
