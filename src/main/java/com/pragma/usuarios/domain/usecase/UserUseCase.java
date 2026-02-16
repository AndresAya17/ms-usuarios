package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.constants.DomainConstants;
import com.pragma.usuarios.domain.exception.DomainException;
import com.pragma.usuarios.domain.exception.ErrorCode;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;
import com.pragma.usuarios.domain.service.UserDomainValidator;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IRestaurantPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPersistencePort passwordEncoderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPersistencePort passwordEncoderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort){
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPersistencePort = passwordEncoderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveUser(User user) {
        UserDomainValidator.validateOwner(user);
        validateUserUniqueness(user);
        user.setRol(new Rol(DomainConstants.OWNER_ID));
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveClient(User user) {
        UserDomainValidator.validateUser(user);
        validateUserUniqueness(user);
        user.setRol(new Rol(DomainConstants.CLIENT_ID));
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        userPersistencePort.saveUser(user);
    }

    @Override
    public void saveEmployee(User employee, Long restaurantId, Long userId) {
        restaurantPersistencePort.validateOwner(restaurantId, userId);
        UserDomainValidator.validateUser(employee);
        validateUserUniqueness(employee);
        employee.setRol(new Rol(DomainConstants.EMPLOYEE_ID));
        String encryptedPassword =
                passwordEncoderPersistencePort.encode(employee.getPassword());
        employee.setPassword(encryptedPassword);
        Long employeeId = userPersistencePort.saveEmployee(employee);
        restaurantPersistencePort.createEmployeeRestaurant(employeeId, restaurantId);
    }

    private void validateUserUniqueness(User user) {

        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "Email already exists"
            );
        }

        if (userPersistencePort.existsByDocumentNumber(user.getDocumentNumber())) {
            throw new DomainException(
                    ErrorCode.INVALID_USER,
                    "Document number already exists"
            );
        }
    }


}
