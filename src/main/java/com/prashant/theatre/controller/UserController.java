package com.prashant.theatre.controller;

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

import com.lowagie.text.BadElementException;
import com.prashant.theatre.dto.BookingResponseDto;
import com.prashant.theatre.dto.TicketSummaryDto;
import com.prashant.theatre.model.Seat;
import com.prashant.theatre.service.BookingService;
import com.prashant.theatre.service.PdfService;
import com.prashant.theatre.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PdfService pdfService;

	@GetMapping("/bookings/{userId}")
	public ResponseEntity<List<BookingResponseDto>> getUserBookings(@PathVariable int userId) {
		return ResponseEntity.ok(userService.getBookingsByUser(userId));
	}
	@GetMapping("/available-seats")
	public ResponseEntity<List<Seat>> getAvailableSeats(@RequestParam String showId) {
	    return ResponseEntity.ok(bookingService.getAvailableSeats(showId));
	}
	@GetMapping("/print-ticket")
	public ResponseEntity<byte[]> printTicket(@RequestParam int userId, @RequestParam String showId) throws Exception {
	    TicketSummaryDto summary = bookingService.getTicketSummary(userId, showId);
	    byte[] pdfBytes = pdfService.generatePdf(summary);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDispositionFormData("attachment", "ticket.pdf");

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(pdfBytes);
	}


}
