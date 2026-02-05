package com.pragma.usuarios.domain.api;


import com.pragma.usuarios.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user);

    void saveClient(User user);

    Long saveEmployee(User employee);
}
