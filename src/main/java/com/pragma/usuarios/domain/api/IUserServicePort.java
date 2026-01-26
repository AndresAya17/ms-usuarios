package com.pragma.usuarios.domain.api;

import com.pragma.usuarios.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user);

    boolean isOwner(Long userId);
}
