package com.pragma.usuarios.infrastructure.configuration;

import com.pragma.usuarios.domain.api.ILoginServicePort;
import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.spi.IJwtPersistencePort;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPersistencePort;
import com.pragma.usuarios.domain.spi.IRestaurantPersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.domain.usecase.LoginUseCase;
import com.pragma.usuarios.domain.usecase.UserUseCase;
import com.pragma.usuarios.infrastructure.input.security.JwtFilter;
import com.pragma.usuarios.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IPasswordEncoderPersistencePort passwordEncoderPersistencePort;
    private final IJwtPersistencePort jwtPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository,userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(){ return new UserUseCase(userPersistencePort(), passwordEncoderPersistencePort, restaurantPersistencePort);
    }

    @Bean
    public ILoginServicePort loginServicePort(){ return new LoginUseCase(userPersistencePort(),passwordEncoderPersistencePort, jwtPersistencePort);
    }

}