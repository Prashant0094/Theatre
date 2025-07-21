package com.prashant.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.theatre.model.Show;

public interface ShowRepo extends JpaRepository<Show, String> {

}
