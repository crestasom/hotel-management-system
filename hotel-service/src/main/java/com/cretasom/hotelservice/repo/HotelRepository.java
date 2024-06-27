package com.cretasom.hotelservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cretasom.hotelservice.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {
	Optional<Hotel> findByHotelId(String hotelId);
}
