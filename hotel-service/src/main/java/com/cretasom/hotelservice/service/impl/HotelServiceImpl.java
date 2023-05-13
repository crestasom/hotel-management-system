package com.cretasom.hotelservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cretasom.hms.exception.ResourceNotFoundException;
import com.cretasom.hotelservice.entity.Hotel;
import com.cretasom.hotelservice.repo.HotelRepository;
import com.cretasom.hotelservice.service.HotelService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

	private HotelRepository repo;

	public Hotel saveHotel(Hotel hotel) {
		hotel.setHotelId(UUID.randomUUID().toString());
		return repo.save(hotel);
	}

	public List<Hotel> getAllHotel() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Hotel getHotel(String hotelId) {
		return repo.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
	}

}
