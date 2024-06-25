package com.cretasom.hotelservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(HotelController.class);
	private HotelService hotelService;

	@PostMapping
	public ResponseEntity<Hotel> create(@RequestBody Hotel hotel) {
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotel));

	}

	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> get(@PathVariable String hotelId) {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotel(hotelId));
	}

	@GetMapping
	public ResponseEntity<List<Hotel>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotel());
	}
}
