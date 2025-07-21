package com.prashant.theatre.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.prashant.theatre.model.Booking;
import com.prashant.theatre.model.Seat;
import com.prashant.theatre.model.Show;
import com.prashant.theatre.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
	private String showId;
	private List<String> seatIds;
	private int userId;

	public Booking toEntity(User user, Show show, Seat seat) {
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShow(show);
		booking.setSeat(seat);
		booking.setBookingTime(LocalDateTime.now());
		return booking;
	}
}

