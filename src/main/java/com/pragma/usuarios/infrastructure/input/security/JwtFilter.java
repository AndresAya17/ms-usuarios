package com.pragma.usuarios.infrastructure.input.security;

import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final IJwtPersistencePort persistencePort;

    public JwtFilter(IJwtPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(BEARER_PREFIX.length());

            if (persistencePort.validateToken(token)) {
                request.setAttribute("auth.userId", persistencePort.getUserId(token));
                request.setAttribute("auth.rol", persistencePort.getRol(token));
            }
        }

        filterChain.doFilter(request, response);
    }
}
