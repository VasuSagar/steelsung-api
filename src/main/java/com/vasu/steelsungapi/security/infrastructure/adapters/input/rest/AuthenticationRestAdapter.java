package com.vasu.steelsungapi.security.infrastructure.adapters.input.rest;

import com.vasu.steelsungapi.security.application.ports.input.RegisterUserUseCase;
import com.vasu.steelsungapi.security.domain.model.AuthenticationResponse;
import com.vasu.steelsungapi.security.domain.model.LoginRequest;
import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://steelsung.vasusagar.com"})
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestAdapter {
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest){
        registerUserUseCase.registerUser(registerUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(registerUserUseCase.loginUser(loginRequest),HttpStatus.OK);
    }
}
