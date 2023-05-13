package com.cretasom.userservice.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cretasom.userservice.entity.Hotel;

@FeignClient(name = "hotel-service")
public interface HotelService {
	@GetMapping("/hotel/{hotelId}")
	Hotel getHotel(@PathVariable String hotelId);

	@GetMapping("/hotels/{hotelId}")
	Hotel CreateHotel(@PathVariable String hotelId);

}
