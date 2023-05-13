package com.cretasom.hotelservice.service;

import java.util.List;

import com.cretasom.hotelservice.entity.Hotel;





public interface HotelService {

	Hotel saveHotel(Hotel hotel);

	List<Hotel> getAllHotel();

	Hotel getHotel(String userId);
}
