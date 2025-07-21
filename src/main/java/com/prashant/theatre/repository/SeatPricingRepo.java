package com.prashant.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.theatre.model.SeatPricing;

public interface SeatPricingRepo extends JpaRepository<SeatPricing, String> {

}
