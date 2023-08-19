package com.vasu.steelsungapi.security.application.ports.output;

import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;

public interface RegisterOutputPort {
    void registerUser(RegisterUserRequest registerUserRequest);
}
