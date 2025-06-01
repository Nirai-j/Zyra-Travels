package com.zyra.service.Impl;

import com.zyra.dto.AuthenticationResponse;
import com.zyra.dto.LoginDTO;
import com.zyra.dto.RegisterDTO;
import com.zyra.model.Role;
import com.zyra.model.User;
import com.zyra.repository.RoleRepository;
import com.zyra.repository.UserRepository;
import com.zyra.security.JwtTokenProvider;
import com.zyra.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterDTO registerRequest) {
        // Check if username exists
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        // Check if email exists
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        // Create new user
        User user = User.builder()
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .username(registerRequest.getUsername())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .phoneNumber(registerRequest.getPhoneNumber())
            .build();

        // Add default role - Fix for detached entity error
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("Default role not found."));
        roles.add(userRole);
        user.setRoles(roles);

        // Save user
        user = userRepository.save(user);

        // Generate token directly without re-authentication
        String jwt = tokenProvider.generateTokenFromUsername(user.getUsername());

        return AuthenticationResponse.builder()
            .token(jwt)
            .type("Bearer")
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
            .build();
    }

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsernameOrEmail(loginDTO.getUsername(), loginDTO.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        return AuthenticationResponse.builder()
            .token(jwt)
            .type("Bearer")
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
            .build();
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
