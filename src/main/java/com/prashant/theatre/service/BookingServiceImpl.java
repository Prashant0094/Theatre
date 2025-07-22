package com.prashant.theatre.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	private List<String> findDuplicates(List<String> seats) {
	    Set<String> seen = new HashSet<>();
	    return seats.stream()
	        .filter(seat -> !seen.add(seat))
	        .distinct()
	        .collect(Collectors.toList());
	}

	public BookingResultDto bookSeats(BookingRequestDto request,int userId) {
	    Show show = showRepo.findById(request.getShowId())
	        .orElseThrow(() -> new RuntimeException("Show not found"));

	    //User user = userRepo.findById(request.getUserId())
	      //  .orElseThrow(() -> new RuntimeException("User not found"));
	    List<String> seatIds = request.getSeatIds();
	    Set<String> uniqueSeatIds = new HashSet<>(seatIds);
	    if (uniqueSeatIds.size() < seatIds.size()) {
	        throw new IllegalArgumentException("Duplicate seat IDs in booking request: " +
	            findDuplicates(seatIds));
	    }

	    List<Seat> seatsToBook = seatRepo.findAllBySeatIdInAndShow_ShowId(
	    	    new ArrayList<>(uniqueSeatIds), request.getShowId());

	    List<BookingResponseDto> bookings = new ArrayList<>();
	    int total = 0;
	    User user = userRepo.findById(userId)
	    	    .orElseThrow(() -> new RuntimeException("User not found"));

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
	     showRepo.findById(showId)
	            .orElseThrow(() -> new RuntimeException("Show not found"));
	        
	        return seatRepo.findByShow_ShowIdAndBookedFalse(showId);
	}

	@Override
	public TicketSummaryDto getTicketSummary(String showId, Principal principal) {
	    // Step 1: Get authenticated user's email
	    String email = principal.getName();

	    // Step 2: Fetch User by email
	    User user = userRepo.findByUserEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    // Step 3: Fetch Show
	    Show show = showRepo.findById(showId)
	        .orElseThrow(() -> new RuntimeException("Show not found"));

	    // Step 4: Get bookings for this user and show
	    List<Booking> bookings = bookingRepo.findByUserUserId(user.getUserId())
	        .stream()
	        .filter(b -> b.getShow().getShowId().equals(showId))
	        .toList();

	    // Step 5: Collect seat IDs and calculate total price
	    List<String> seats = new ArrayList<>();
	    int total = 0;

	    for (Booking b : bookings) {
	        String seatId = b.getSeat().getSeatId();
	        seats.add(seatId);

	        String row = seatId.substring(0, 1); // Assuming seatId like A3 or B5
	        int price = seatPricingRepo.findById(row)
	            .orElseThrow(() -> new RuntimeException("No pricing for row: " + row))
	            .getPrice();

	        total += price;
	    }

	    // Step 6: Build and return summary DTO
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