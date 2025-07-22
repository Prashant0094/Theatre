package com.prashant.theatre.service;

import java.util.List;

import com.prashant.theatre.dto.BookingResponseDto;

public interface UserService {
	public List<BookingResponseDto> getBookingsByUser(int userId);

	public Object findByUserEmail(String email);
}
