package com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.security.application.ports.output.RegisterOutputPort;
import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response.ArgumentNotValidException;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class RegisterPersistenceAdapter implements RegisterOutputPort {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        if(StringUtils.containsWhitespace(registerUserRequest.getUsername())){
            throw new ArgumentNotValidException("username");
        }

       User user= User.builder()
                .username(registerUserRequest.getUsername())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .build();
        userRepository.save(user);
    }
}
