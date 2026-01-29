package com.pragma.usuarios.application.handler;

import com.pragma.usuarios.application.dto.request.UserRequestDto;
import com.pragma.usuarios.application.handler.impl.UserHandler;
import com.pragma.usuarios.application.mapper.IUserRequestMapper;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {
    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @InjectMocks
    private UserHandler userHandler;

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

}
