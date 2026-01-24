package com.pragma.usuarios.domain.model;

import com.pragma.usuarios.domain.exception.UnderageUserException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void noDeberiaLanzarExcepcionCuandoUserEsMayorDeEdad() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(20));
        user.setRol(Rol.PROPIETARIO);

        assertDoesNotThrow(user::validateIsAdult);
    }

    @Test
    void deberiaLanzarExcepcionCuandoUserEsMenorDeEdad() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(17));
        user.setRol(Rol.PROPIETARIO);

        assertThrows(
                UnderageUserException.class,
                user::validateIsAdult);
    }

}
