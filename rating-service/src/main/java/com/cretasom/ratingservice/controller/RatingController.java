package com.cretasom.ratingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cretasom.ratingservice.entity.Rating;

import com.cretasom.ratingservice.service.RatingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/rating")
public class RatingController {

	private RatingService ratingService;

	@PostMapping
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.saveRating(rating));

	}

	@GetMapping("/{ratingId}")
	public ResponseEntity<Rating> getRating(@PathVariable String ratingId) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRating(ratingId));
	}

	@GetMapping
	public ResponseEntity<List<Rating>> getAllRating() {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRating());
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Rating>> getAllRatingByUserId(@PathVariable String userId) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByUserId(userId));
	}

	@GetMapping("/hotel/{hotelId}")
	public ResponseEntity<List<Rating>> getAllRatingByHotelId(@PathVariable String hotelId) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByHotelId(hotelId));
	}

}
