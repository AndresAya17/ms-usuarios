package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.exception.UserNotFoundException;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.exception.UnauthorizedException;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPersistencePort passwordEncoderPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPersistencePort passwordEncoderPersistencePort){
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPersistencePort = passwordEncoderPersistencePort;
    }


    @Override
    public void saveUser(User user, String rol) {
        if (!Rol.ADMINISTRADOR.name().equals(rol)){
            throw new UnauthorizedException("You don't have permissions");
        }
        user.validateOwner();
        user.setRol(Rol.PROPIETARIO);
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        userPersistencePort.saveUser(user);
    }


}
