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
public class UserHandlerTest {
    @Mock
    private IUserServicePort propietarioServicePort;

    @Mock
    private IUserRequestMapper propietarioRequestMapper;

    @InjectMocks
    private UserHandler usuarioHandler;

    @Test
    void deberiaMapearYGuardarUserCorrectamente() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto();
        User user = new User();

        when(propietarioRequestMapper.toUser(requestDto))
                .thenReturn(user);

        // Act
        usuarioHandler.saveOwner(requestDto);

        // Assert
        verify(propietarioRequestMapper, times(1))
                .toUser(requestDto);

        verify(propietarioServicePort, times(1))
                .saveUser(user);

        verifyNoMoreInteractions(propietarioRequestMapper, propietarioServicePort);
    }

}
