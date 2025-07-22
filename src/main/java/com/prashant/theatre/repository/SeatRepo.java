package com.prashant.theatre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.theatre.model.Seat;
import com.prashant.theatre.model.Show;

public interface SeatRepo extends JpaRepository<Seat, Long> {
	
    List<Seat> findByShowShowId(String showId); 
    List<Seat> findByShow(Show show);
    List<Seat> findByShow_ShowIdAndBookedFalse(String showId);
    
    List<Seat> findAllBySeatIdInAndShow_ShowId(List<String> seatIds, String showId);



}
