package com.prashant.theatre.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.theatre.dto.AuthenticationRequest;
import com.prashant.theatre.dto.AuthenticationResponse;
import com.prashant.theatre.dto.UserRequestDto;
import com.prashant.theatre.dto.UserResponseDto;
import com.prashant.theatre.jwt.JwtService;
import com.prashant.theatre.model.Role;
import com.prashant.theatre.model.User;
import com.prashant.theatre.repository.UserRepo;
import com.prashant.theatre.security.CustomUserDetailsService;

@Service
public class AuthService {

 @Autowired
 private AuthenticationManager authenticationManager;

 @Autowired
 private CustomUserDetailsService userDetailsService;

 @Autowired
 private JwtService jwtService;

 @Autowired
 private UserRepo userRepo;

 @Autowired
 private BCryptPasswordEncoder passwordEncoder;
 
 private static final Logger log = LoggerFactory.getLogger(AuthService.class);

 public AuthenticationResponse login(AuthenticationRequest request) {
     authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(
                     request.getEmail(),
                     request.getPassword())
     );

     UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
     String token = jwtService.generateToken(user.getUsername());
     log.info("Token generated for User with email: {} " , request.getEmail());
     return new AuthenticationResponse(token);
 }


 public UserResponseDto registerUser(UserRequestDto dto) {
     User user = dto.toEntity();
     user.setRole(Role.USER); // Force role to USER, ignore incoming
     user.setPassword(passwordEncoder.encode(dto.getPassword()));
     return UserResponseDto.fromEntity(userRepo.save(user));
 }

public UserResponseDto createUser(UserRequestDto dto) {
    User user = dto.toEntity();
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    return UserResponseDto.fromEntity(userRepo.save(user));
}
}

