package com.zyra.service;

import com.zyra.model.User;

public interface AuthService {
    User register(User user);
    User login(String usernameOrEmail, String password);
    void logout();
}
