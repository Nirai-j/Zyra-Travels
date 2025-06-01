package com.zyra.config;

import com.zyra.model.Role;
import com.zyra.model.User;
import com.zyra.repository.RoleRepository;
import com.zyra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (!userRepository.existsByUsername("admin")) {
            System.out.println("Creating admin user...");
            
            // Create admin role if it doesn't exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_ADMIN");
                    return roleRepository.save(newRole);
                });
            
            // Create user role if it doesn't exist
            Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    return roleRepository.save(newRole);
                });
            
            // Create admin user
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setUsername("admin");
            admin.setEmail("admin@zyra.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);
            admin.setRoles(roles);
            
            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
} 