package com.vasu.steelsungapi.security.application.ports.input;

import com.vasu.steelsungapi.security.domain.model.AuthenticationResponse;
import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;

public interface RegisterUserUseCase {
    void registerUser(RegisterUserRequest registerUserRequest);
}
