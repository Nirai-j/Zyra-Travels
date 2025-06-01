package com.zyra.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileDTO {
    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 3, message = "Username should have at least 3 characters")
    private String username;
    
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String postalCode;
} 