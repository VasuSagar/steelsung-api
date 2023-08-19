package com.vasu.steelsungapi.security.infrastructure.adapters.config;

import com.vasu.steelsungapi.security.domain.service.AuthenticationService;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.CustomUserDetailsService;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.JwtAuthenticationFilter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.JwtService;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityBeanConfiguration {
    private final CustomUserDetailsService customUserDetailsService;
    //private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public RegisterPersistenceAdapter registerPersistenceAdapter(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        return new RegisterPersistenceAdapter(userRepository,passwordEncoder,jwtService,authenticationManager);
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
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()).authorizeHttpRequests(authorize-> {
                    try {
                        authorize
                                .requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                        .anyRequest()
                                        .authenticated()
                                        .and()
                                        .sessionManagement()
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                        .and()
                                        .authenticationProvider(authenticationProvider())
                                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                );
        return http.build();
    }
}
