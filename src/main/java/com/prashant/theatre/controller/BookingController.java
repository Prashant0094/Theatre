package com.prashant.theatre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.theatre.dto.BookingRequestDto;
import com.prashant.theatre.dto.BookingResultDto;
import com.prashant.theatre.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResultDto> bookSeats(@RequestBody BookingRequestDto request) {
        return ResponseEntity.ok(bookingService.bookSeats(request));
    }
}
