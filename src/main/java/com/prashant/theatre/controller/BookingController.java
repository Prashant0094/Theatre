package com.prashant.theatre.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.theatre.dto.BookingRequestDto;
import com.prashant.theatre.dto.BookingResultDto;
import com.prashant.theatre.model.User;
import com.prashant.theatre.repository.UserRepo;
import com.prashant.theatre.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/book")
    public ResponseEntity<BookingResultDto> bookSeats(@RequestBody BookingRequestDto request, Principal principal) {
        String email = principal.getName(); // This comes from the JWT token
        User user = userRepo.findByUserEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(bookingService.bookSeats(request, user.getUserId()));

    }

}
