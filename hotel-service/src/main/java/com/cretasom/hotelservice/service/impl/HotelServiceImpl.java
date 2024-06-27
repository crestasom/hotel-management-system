package com.cretasom.hotelservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

	@Override
	public Hotel saveHotel(Hotel hotel) {
		hotel.setHotelId(UUID.randomUUID().toString());
		return repo.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotel() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Hotel getHotel(String hotelId) {
		log.info("hotelId [{}]" + hotelId);
		return repo.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
	}

}
