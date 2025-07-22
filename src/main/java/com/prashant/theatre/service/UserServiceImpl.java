package com.prashant.theatre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashant.theatre.dto.BookingResponseDto;
import com.prashant.theatre.repository.BookingRepo;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BookingRepo bookingRepo;
	
	@Override
	public List<BookingResponseDto> getBookingsByUser(int userId) {
		
		return bookingRepo.findByUserUserId(userId)
						.stream()
						.map(BookingResponseDto::fromEntity)
						.toList();
	}

	@Override
	public Object findByUserEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
