package com.zyra.service;

import com.zyra.dto.UpdateProfileDTO;
import com.zyra.dto.UserDTO;
import com.zyra.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    UserDTO getUserById(Long id);
    List<User> getAllUsers();
    UserDTO updateUser(Long id, UpdateProfileDTO updateProfileDTO);
    void deleteUser(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUsernameOrEmail(String usernameOrEmail);
    UserDTO updateUserProfile(String email, UpdateProfileDTO updateProfileDTO);
}
