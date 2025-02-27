package com.example.userregistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.userregistration.model.UserRegistrationRequestDto;
import com.example.userregistration.model.UserRegistrationResponseDto;
import com.example.userregistration.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserRegistrationResponseDto> registerUser(
			@Valid @RequestBody UserRegistrationRequestDto requestDto) {
		return ResponseEntity.ok(userService.registerUser(requestDto));
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserRegistrationResponseDto>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PostMapping("/validate")
	public ResponseEntity<String> validateUser(@RequestParam String email, @RequestParam String password) {
		return ResponseEntity.ok(userService.validateUser(email, password));
	}

	@DeleteMapping("/delete/{email}")
	public ResponseEntity<Void> deleteUser(@PathVariable String email) {
		userService.deleteUser(email);
		return ResponseEntity.ok().build();
	}
}
