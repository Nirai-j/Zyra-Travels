package com.zyra.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zyra.repository.UserRepository;
import com.zyra.model.User;
import com.zyra.service.UserService; // Import the UserService class

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
    }
    
    @Test
    void testFindUserById() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        
        User result = userService.findById(1L);
        
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
    }
    
    @Test
    void testFindUserByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            userService.findById(999L);
        });
    }
}