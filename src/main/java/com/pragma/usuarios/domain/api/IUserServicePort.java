package com.pragma.usuarios.domain.api;


import com.pragma.usuarios.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user);

    void saveClient(User user);

    void saveEmployee(User employee, Long restaurantId, Long userId);
}
