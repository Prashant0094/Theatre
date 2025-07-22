package com.prashant.theatre.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.prashant.theatre.dto.BookingResponseDto;
import com.prashant.theatre.dto.TicketSummaryDto;
import com.prashant.theatre.model.Seat;
import com.prashant.theatre.model.User;
import com.prashant.theatre.repository.UserRepo;
import com.prashant.theatre.service.BookingService;
import com.prashant.theatre.service.PdfService;
import com.prashant.theatre.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PdfService pdfService;

	@GetMapping("/bookings")
	public ResponseEntity<List<BookingResponseDto>> getUserBookings(Principal principal) {
	    String email = principal.getName();
	    User user = userRepo.findByUserEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    return ResponseEntity.ok(userService.getBookingsByUser(user.getUserId()));
	}

	@GetMapping("/available-seats")
	public ResponseEntity<List<Seat>> getAvailableSeats(@RequestParam String showId) {
	    return ResponseEntity.ok(bookingService.getAvailableSeats(showId));
	}
	@GetMapping("/print-ticket")
	public ResponseEntity<byte[]> printTicket(@RequestParam String showId, Principal principal) throws Exception {
	    String email = principal.getName(); // email from the JWT token
	    User user = userRepo.findByUserEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    TicketSummaryDto summary = bookingService.getTicketSummary( showId, principal);
	    byte[] pdfBytes = pdfService.generatePdf(summary);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDispositionFormData("attachment", "ticket.pdf");

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(pdfBytes);
	}



}
