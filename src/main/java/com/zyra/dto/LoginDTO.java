package com.zyra.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "Username/email is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}