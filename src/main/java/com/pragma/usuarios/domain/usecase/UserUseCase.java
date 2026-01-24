package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort){
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public void saveUser(User user) {
        user.setRol(Rol.PROPIETARIO);
        user.validateIsAdult();
        userPersistencePort.saveUser(user);
    }
}
