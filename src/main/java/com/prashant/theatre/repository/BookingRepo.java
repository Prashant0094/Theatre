package com.prashant.theatre.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prashant.theatre.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer> {
	List<Booking> findByUserUserId(int userId);

	List<Booking> findByShowShowId(String showId);
}
