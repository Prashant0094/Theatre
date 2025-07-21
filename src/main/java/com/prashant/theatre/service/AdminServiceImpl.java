package com.prashant.theatre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.theatre.dto.UserRequestDto;
import com.prashant.theatre.dto.UserResponseDto;
import com.prashant.theatre.model.Role;
import com.prashant.theatre.model.Seat;
import com.prashant.theatre.model.SeatPricing;
import com.prashant.theatre.model.Show;
import com.prashant.theatre.model.User;
import com.prashant.theatre.repository.SeatPricingRepo;
import com.prashant.theatre.repository.SeatRepo;
import com.prashant.theatre.repository.ShowRepo;
import com.prashant.theatre.repository.UserRepo;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ShowRepo showRepo;

	@Autowired
	private SeatPricingRepo pricingRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SeatRepo seatRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final List<String> DEFAULT_SEATS = List.of(
		    "A1", "A2", "A3", "A4", "A5", "B1", "B2", "B3", "B4", "B5"
		);

	public Show createOrUpdateShow(Show show) {
	    Show savedShow = showRepo.save(show);

	    for (String seatId : DEFAULT_SEATS) {
	        Seat seat = new Seat();
	        seat.setSeatId(seatId);
	        seat.setBooked(false);
	        seat.setShow(savedShow);
	        seatRepo.save(seat);
	    }

	    return savedShow;
	}

	public SeatPricing updateSeatPricing(String seatRow, int price) {
		SeatPricing pricing = new SeatPricing(seatRow, price);
		return pricingRepo.save(pricing);
	}

	public List<Show> getAllShows() {
		return showRepo.findAll();
	}

	public UserResponseDto createUser(UserRequestDto dto) {
		User user = dto.toEntity();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(Role.ADMIN);
		return UserResponseDto.fromEntity(userRepo.save(user));
	}
}
