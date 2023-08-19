package com.vasu.steelsungapi.security.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "username must not be empty")
    @Size(min = 4, max = 15,message = "username must be of 4-15 characters long")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 4, max = 20,message = "password must be of 4-20 characters long")
    private String password;
}
