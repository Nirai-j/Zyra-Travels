package com.zyra.service.Impl;

import com.zyra.model.User;
import com.zyra.repository.UserRepository;
import com.zyra.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        // Implement registration logic
        return userRepository.save(user);
    }

    @Override
    public User login(String usernameOrEmail, String password) {
        // Implement authentication logic
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public void logout() {
        // Implement logout logic
    }
}
