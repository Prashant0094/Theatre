package com.prashant.theatre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.theatre.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUserEmail(String email);

}
