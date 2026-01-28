package com.pragma.usuarios.application.util;

import jakarta.servlet.http.HttpServletRequest;

public class AuthContext {

    public static Long getUserId(HttpServletRequest request) {
        Object userId = request.getAttribute("auth.userId");
        return userId != null ? (Long) userId : null;
    }

    public static String getRol(HttpServletRequest request) {
        Object rol = request.getAttribute("auth.rol");
        return rol != null ? rol.toString() : null;
    }
}
