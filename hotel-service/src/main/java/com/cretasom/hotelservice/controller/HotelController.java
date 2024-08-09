package com.cretasom.hotelservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cretasom.hotelservice.entity.Hotel;
import com.cretasom.hotelservice.service.HotelService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

//	static final Counter counter =Counter.build().name("t")
	private HotelService hotelService;

	@PostMapping
	public ResponseEntity<Hotel> createUser(@RequestBody Hotel hotel) {
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotel));

	}

	@GetMapping("/{userId}")
	public ResponseEntity<Hotel> getUser(@PathVariable String userId) {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotel(userId));
	}

	@GetMapping
	public ResponseEntity<List<Hotel>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotel());
	}
}
