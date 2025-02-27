package com.example.userregistration.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}
}
