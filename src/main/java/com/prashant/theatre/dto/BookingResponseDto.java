package com.prashant.theatre.dto;

import java.time.LocalDateTime;

import com.prashant.theatre.model.Booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
	private int bookingId;
	private String showId;
	private String seatId;
	private String userName;
	private LocalDateTime bookingTime;

	public static BookingResponseDto fromEntity(Booking booking) {
		return new BookingResponseDto(
			booking.getBookingId(),
			booking.getShow().getShowId(),
			booking.getSeat().getSeatId(),
			booking.getUser().getUserName(),
			booking.getBookingTime()
		);
	}
}

