package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user, String rol);
}
