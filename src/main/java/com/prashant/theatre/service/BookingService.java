package com.prashant.theatre.service;

import java.util.List;

import com.prashant.theatre.dto.BookingRequestDto;
import com.prashant.theatre.dto.BookingResultDto;
import com.prashant.theatre.dto.TicketSummaryDto;
import com.prashant.theatre.model.Seat;

public interface BookingService {
	public BookingResultDto bookSeats(BookingRequestDto request);

	public List<Seat> getAvailableSeats(String showId);
	public TicketSummaryDto getTicketSummary(int userId, String showId);
	
}
