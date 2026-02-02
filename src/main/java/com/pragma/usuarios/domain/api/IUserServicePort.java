package com.pragma.usuarios.domain.api;


import com.pragma.usuarios.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user, String rol);

    void saveClient(User user);

    Long saveEmployee(User employee, String rol);
}
