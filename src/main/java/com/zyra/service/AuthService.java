package com.zyra.service;

import com.zyra.dto.AuthenticationResponse;
import com.zyra.dto.LoginDTO;
import com.zyra.dto.RegisterDTO;

public interface AuthService {
    AuthenticationResponse register(RegisterDTO registerRequest);
    AuthenticationResponse login(LoginDTO loginDTO);
    void logout();
}
