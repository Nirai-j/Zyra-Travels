package com.zyra;

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
import com.zyra.service.Impl.UserServiceImpl;
import com.zyra.dto.UserDTO;
import com.zyra.dto.UpdateProfileDTO;
import com.zyra.exception.ResourceNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    private User testUser;
    private UpdateProfileDTO updateProfileDTO;
    
    @BeforeEach
    void setUp() {
        testUser = User.builder()
            .id(1L)
            .firstName("Test")
            .lastName("User")
            .username("testuser")
            .email("test@example.com")
            .build();

        updateProfileDTO = new UpdateProfileDTO();
        updateProfileDTO.setFirstName("Updated");
        updateProfileDTO.setLastName("User");
        updateProfileDTO.setUsername("testuser");
        updateProfileDTO.setEmail("test@example.com");
    }
    
    @Test
    void getUserById_ShouldReturnUserDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        
        UserDTO result = userService.getUserById(1L);
        
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
    }
    
    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUserDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDTO result = userService.updateUser(1L, updateProfileDTO);

        assertNotNull(result);
        assertEquals(updateProfileDTO.getFirstName(), result.getFirstName());
        assertEquals(updateProfileDTO.getLastName(), result.getLastName());
        verify(userRepository).save(any(User.class));
    }
}