package com.example.userregistration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.userregistration.model.User;
import com.example.userregistration.model.UserRegistrationRequestDto;
import com.example.userregistration.model.UserRegistrationResponseDto;
import com.example.userregistration.repository.UserRepository;

class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private LocationService locationService;
    
    @InjectMocks
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testRegisterUser() {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        dto.setName("Test User");
        dto.setGender("Male");
        
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(locationService.getIpAddress()).thenReturn("1.1.1.1");
        when(locationService.getCountry(anyString())).thenReturn("India");
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        UserRegistrationResponseDto result = userService.registerUser(dto);
        assertNotNull(result);
        assertEquals(result.getEmail(), user.getEmail());
        assertEquals(result.getName(), user.getName());
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void testValidateUser() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setPassword("password");
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "password")).thenReturn(true);
        
        String result = userService.validateUser(email, password);
        assertEquals("Valid User",result);
    }
    
    @Test
    void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserRegistrationResponseDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }
    
    @Test
    void testDeleteUser() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        userService.deleteUser(email);
        verify(userRepository, times(1)).existsByEmail(email);
        verify(userRepository, times(1)).deleteByEmail(email);
    }
}