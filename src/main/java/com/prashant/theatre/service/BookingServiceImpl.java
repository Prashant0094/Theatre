package com.prashant.theatre.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashant.theatre.dto.BookingRequestDto;
import com.prashant.theatre.dto.BookingResponseDto;
import com.prashant.theatre.dto.BookingResultDto;
import com.prashant.theatre.dto.TicketSummaryDto;
import com.prashant.theatre.model.Booking;
import com.prashant.theatre.model.Seat;
import com.prashant.theatre.model.Show;
import com.prashant.theatre.model.User;
import com.prashant.theatre.repository.BookingRepo;
import com.prashant.theatre.repository.SeatPricingRepo;
import com.prashant.theatre.repository.SeatRepo;
import com.prashant.theatre.repository.ShowRepo;
import com.prashant.theatre.repository.UserRepo;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ShowRepo showRepo;

	@Autowired
	private SeatRepo seatRepo;
	
	@Autowired
	private SeatPricingRepo seatPricingRepo;

	@Autowired
	private BookingRepo bookingRepo;

	public BookingResultDto bookSeats(BookingRequestDto request) {
	    Show show = showRepo.findById(request.getShowId())
	        .orElseThrow(() -> new RuntimeException("Show not found"));

	    User user = userRepo.findById(request.getUserId())
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    List<Seat> seatsToBook = seatRepo.findAllById(request.getSeatIds());
	    List<BookingResponseDto> bookings = new ArrayList<>();
	    int total = 0;

	    for (Seat seat : seatsToBook) {
	        if (seat.isBooked()) {
	            throw new RuntimeException("Seat already booked: " + seat.getSeatId());
	        }

	        seat.setBooked(true);
	        seatRepo.save(seat);

	        Booking booking = new Booking();
	        booking.setUser(user);
	        booking.setShow(show);
	        booking.setSeat(seat);
	        booking.setBookingTime(LocalDateTime.now());

	        bookingRepo.save(booking);
	        bookings.add(BookingResponseDto.fromEntity(booking));

	        String row = seat.getSeatId().substring(0, 1);
	        int price = seatPricingRepo.findById(row)
	            .orElseThrow(() -> new RuntimeException("No pricing for row: " + row))
	            .getPrice();
	        total += price;
	    }

	    return new BookingResultDto(bookings, total);
	}


	@Override
	public List<Seat> getAvailableSeats(String showId) {
	    Show show = showRepo.findById(showId)
	            .orElseThrow(() -> new RuntimeException("Show not found"));
	        
	        return seatRepo.findByShow_ShowIdAndBookedFalse(showId);
	}
	public TicketSummaryDto getTicketSummary(int userId, String showId) {
	    User user = userRepo.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Show show = showRepo.findById(showId)
	        .orElseThrow(() -> new RuntimeException("Show not found"));

	    List<Booking> bookings = bookingRepo.findByUserUserId(userId)
	        .stream()
	        .filter(b -> b.getShow().getShowId().equals(showId))
	        .toList();

	    List<String> seats = new ArrayList<>();
	    int total = 0;

	    for (Booking b : bookings) {
	        String seatId = b.getSeat().getSeatId();
	        seats.add(seatId);
	        String row = seatId.substring(0, 1);
	        int price = seatPricingRepo.findById(row)
	                .orElseThrow(() -> new RuntimeException("No pricing for row: " + row))
	                .getPrice();
	        total += price;
	    }

	    return new TicketSummaryDto(
	        user.getUserName(),
	        user.getUserEmail(),
	        show.getShowName(),
	        show.getShowId(),
	        show.getShowLength(),
	        seats,
	        total
	    );
	}

}