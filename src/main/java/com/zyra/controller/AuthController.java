package com.zyra.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zyra.dto.AuthenticationResponse;
import com.zyra.dto.LoginDTO;
import com.zyra.dto.RegisterDTO;
import com.zyra.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerRequest) {
        try {
            log.debug("Received registration request for username: {}", registerRequest.getUsername());
            AuthenticationResponse response = authService.register(registerRequest);
            log.debug("Successfully registered user: {}", registerRequest.getUsername());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Registration failed for username: {}, error: {}", registerRequest.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            log.debug("Received login request for username: {}", loginDTO.getUsername());
            AuthenticationResponse response = authService.login(loginDTO);
            log.debug("Successfully logged in user: {}", loginDTO.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Login failed for username: {}, error: {}", loginDTO.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        log.debug("Processing logout request");
        authService.logout();
        log.debug("Successfully logged out user");
        return ResponseEntity.ok("{\"message\": \"Logged out successfully\"}");
    }
}