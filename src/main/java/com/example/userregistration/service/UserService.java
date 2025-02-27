package com.example.userregistration.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userregistration.exception.UserAlreadyExistsException;
import com.example.userregistration.model.User;
import com.example.userregistration.model.UserRegistrationRequestDto;
import com.example.userregistration.model.UserRegistrationResponseDto;
import com.example.userregistration.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LocationService locationService;

	public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto requestDto) {
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new UserAlreadyExistsException("Email already registered");
		}

		User user = new User();
		user.setName(requestDto.getName());
		user.setEmail(requestDto.getEmail());
		user.setGender(requestDto.getGender());
		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

		String ipAddress = locationService.getIpAddress();
		String country = locationService.getCountry(ipAddress);

		user.setIpAddress(ipAddress);
		user.setCountry(country);

		User savedUser = userRepository.save(user);
		return convertToDto(savedUser);
	}

	private UserRegistrationResponseDto convertToDto(User user) {
		UserRegistrationResponseDto dto = new UserRegistrationResponseDto();
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setGender(user.getGender());
		dto.setCountry(user.getCountry());
		return dto;
	}

	public String validateUser(String email, String password) {
		boolean isValid =  userRepository.findByEmail(email).map(user -> passwordEncoder.matches(password, user.getPassword()))
				.orElse(false);
		return isValid ? "Valid User" : "Invalid User";
	}

	public List<UserRegistrationResponseDto> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToDto).toList();
	}
	
	@Transactional
	public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteByEmail(email);
    }
}
