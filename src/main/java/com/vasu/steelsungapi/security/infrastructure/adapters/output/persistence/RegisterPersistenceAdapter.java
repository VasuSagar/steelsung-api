package com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence;

import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.entity.PlayerState;
import com.vasu.steelsungapi.player.infrastructure.adapters.output.persistence.repository.PlayerStateRepository;
import com.vasu.steelsungapi.security.application.ports.output.RegisterOutputPort;
import com.vasu.steelsungapi.security.domain.model.AuthenticationResponse;
import com.vasu.steelsungapi.security.domain.model.LoginRequest;
import com.vasu.steelsungapi.security.domain.model.RegisterUserRequest;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response.ArgumentNotValidException;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.entity.User;
import com.vasu.steelsungapi.security.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class RegisterPersistenceAdapter implements RegisterOutputPort {
    private final UserRepository userRepository;
    private final PlayerStateRepository playerStateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
        PlayerState playerState = PlayerState.builder().user(user).balanceAmount(Double.valueOf(0.00)).totalBetAmount(0L).experience(0L).level(0).build();
        playerStateRepository.save(playerState);
    }

    @Override
    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));
        User player = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(player);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    @Override
    public User getLoggedInUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Email name not found - " + principal.getUsername()));
    }

    public User getLoggedInUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email name not found - " + username));
    }
}
