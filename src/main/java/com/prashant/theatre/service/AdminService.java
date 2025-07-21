package com.prashant.theatre.service;

import java.util.List;

import com.prashant.theatre.dto.UserRequestDto;
import com.prashant.theatre.dto.UserResponseDto;
import com.prashant.theatre.model.SeatPricing;
import com.prashant.theatre.model.Show;

public interface AdminService {
	public Show createOrUpdateShow(Show show);

	public SeatPricing updateSeatPricing(String seatRow, int price);

	public List<Show> getAllShows();

	public UserResponseDto createUser(UserRequestDto dto);
}
