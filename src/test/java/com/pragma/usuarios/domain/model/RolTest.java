package com.pragma.usuarios.domain.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class RolTest {

    @Test
    void deberiaContenerTodosLosRolesEsperados() {
        Rol[] roles = Rol.values();

        assertEquals(4, roles.length);
        assertTrue(contains(roles, Rol.ADMINISTRADOR));
        assertTrue(contains(roles, Rol.PROPIETARIO));
        assertTrue(contains(roles, Rol.EMPLEADO));
        assertTrue(contains(roles, Rol.CLIENTE));
    }

    @Test
    void valueOfDeberiaRetornarElRolCorrecto() {
        assertEquals(Rol.ADMINISTRADOR, Rol.valueOf("ADMINISTRADOR"));
        assertEquals(Rol.PROPIETARIO, Rol.valueOf("PROPIETARIO"));
        assertEquals(Rol.EMPLEADO, Rol.valueOf("EMPLEADO"));
        assertEquals(Rol.CLIENTE, Rol.valueOf("CLIENTE"));
    }

    private boolean contains(Rol[] roles, Rol rol) {
        for (Rol r : roles) {
            if (r == rol) {
                return true;
            }
        }
        return false;
    }
}
