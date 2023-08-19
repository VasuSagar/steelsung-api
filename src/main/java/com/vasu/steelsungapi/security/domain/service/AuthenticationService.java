package com.vasu.steelsungapi.security.domain.service;

import com.vasu.steelsungapi.security.application.ports.input.RegisterUserUseCase;
import com.vasu.steelsungapi.security.domain.model.AuthenticationResponse;
import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.RegisterPersistenceAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationService implements RegisterUserUseCase {
    private final RegisterPersistenceAdapter registerPersistenceAdapter;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        registerPersistenceAdapter.registerUser(registerUserRequest);
    }
}
