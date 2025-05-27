package com.zyra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotEmpty(message = "Username or email should not be empty")
    private String usernameOrEmail;
    
    @NotEmpty(message = "Password should not be empty")
    private String password;
}