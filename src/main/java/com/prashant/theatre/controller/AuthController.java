package com.prashant.theatre.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prashant.theatre.dto.AuthenticationRequest;
import com.prashant.theatre.dto.AuthenticationResponse;
import com.prashant.theatre.dto.UserRequestDto;
import com.prashant.theatre.dto.UserResponseDto;
import com.prashant.theatre.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
		log.debug("Login attempt for user: {}", request.getEmail());
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto request) {
		return ResponseEntity.ok(authService.registerUser(request));
	}

}
