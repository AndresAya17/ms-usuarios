package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.infrastructure.constants.RoleConstants;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPersistencePort passwordEncoderPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPersistencePort passwordEncoderPersistencePort){
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPersistencePort = passwordEncoderPersistencePort;
    }


    @Override
    public void saveUser(User user) {
        user.validateOwner();
        user.setRol(new Rol(RoleConstants.OWNER_ID));
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveClient(User user) {
        user.setRol(new Rol(RoleConstants.CLIENT_ID));
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        userPersistencePort.saveUser(user);
    }

    @Override
    public Long saveEmployee(User employee) {
        employee.setRol(new Rol(RoleConstants.OWNER_ID));
        employee.validateEmployee();
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(employee.getPassword());

        employee.setPassword(encryptedPassword);
        return userPersistencePort.saveEmployee(employee);

    }


}
