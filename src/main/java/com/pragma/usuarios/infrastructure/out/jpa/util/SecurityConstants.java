package com.pragma.usuarios.infrastructure.out.jpa.util;

public class SecurityConstants {

    private SecurityConstants() {}

    public static final String HAS_ADMIN = "hasAuthority('ADMIN')";
    public static final String HAS_OWNER = "hasAuthority('OWNER')";

}
