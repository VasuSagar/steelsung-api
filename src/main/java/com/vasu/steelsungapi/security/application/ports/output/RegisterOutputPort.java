package com.vasu.steelsungapi.security.application.ports.output;

import com.vasu.steelsungapi.security.domain.model.AuthenticationResponse;
import com.vasu.steelsungapi.security.domain.model.LoginRequest;
import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;

public interface RegisterOutputPort {
    void registerUser(RegisterUserRequest registerUserRequest);

    AuthenticationResponse loginUser(LoginRequest loginRequest);

    User getLoggedInUser();
}
