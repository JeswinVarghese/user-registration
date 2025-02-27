package com.example.userregistration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.userregistration.model.Role;
import com.example.userregistration.model.User;
import com.example.userregistration.repository.UserRepository;
import com.example.userregistration.service.LocationService;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocationService locationService;

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin User");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setGender("Other");
            adminUser.setAccessType(Role.ADMIN);

            String ipAddress = locationService.getIpAddress();
            String country = locationService.getCountry(ipAddress);
            adminUser.setIpAddress(ipAddress);
            adminUser.setCountry(country);

            userRepository.save(adminUser);
            System.out.println("Admin user created successfully");
            System.out.println(userRepository.findAll());
        }
    }
}
