package com.vasu.steelsungapi.security.infrastructure.adapters.config;

import com.vasu.steelsungapi.security.domain.service.AuthenticationService;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeanConfiguration {
    @Bean
    public RegisterPersistenceAdapter registerPersistenceAdapter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new RegisterPersistenceAdapter(userRepository,passwordEncoder);
    }

    @Bean
    public AuthenticationService authenticationService(RegisterPersistenceAdapter registerPersistenceAdapter) {
        return new AuthenticationService(registerPersistenceAdapter);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()).authorizeHttpRequests(authorize->authorize
                .requestMatchers("/api/v1/auth/**")
                .permitAll());
        return http.build();
    }
}
