package com.prashant.theatre.service;

import java.security.Principal;
import java.util.List;

import com.prashant.theatre.dto.BookingRequestDto;
import com.prashant.theatre.dto.BookingResultDto;
import com.prashant.theatre.dto.TicketSummaryDto;
import com.prashant.theatre.model.Seat;

public interface BookingService {
	public BookingResultDto bookSeats(BookingRequestDto request,int userId);

	public List<Seat> getAvailableSeats(String showId);
	public TicketSummaryDto getTicketSummary(String showId, Principal principal);

	
}
