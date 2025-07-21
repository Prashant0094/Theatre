package com.prashant.theatre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.theatre.dto.UserRequestDto;
import com.prashant.theatre.dto.UserResponseDto;
import com.prashant.theatre.model.SeatPricing;
import com.prashant.theatre.model.Show;
import com.prashant.theatre.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/show")
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        return ResponseEntity.ok(adminService.createOrUpdateShow(show));
    }

    @PutMapping("/seat-pricing")
    public ResponseEntity<SeatPricing> updateSeatPrice(@RequestParam String row, @RequestParam int price) {
        return ResponseEntity.ok(adminService.updateSeatPricing(row, price));
    }

    @GetMapping("/shows")
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(adminService.getAllShows());
    }
    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(adminService.createUser(request));
    }

}