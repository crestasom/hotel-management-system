package com.cretasom.ratingservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;


import com.cretasom.hms.exception.ResourceNotFoundException;

import com.cretasom.ratingservice.entity.Rating;

import com.cretasom.ratingservice.repo.RatingRepository;
import com.cretasom.ratingservice.service.RatingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

	private RatingRepository repo;
	

	public Rating saveRating(Rating rating) {
		rating.setRatingId(UUID.randomUUID().toString());
		return repo.save(rating);
	}

	public List<Rating> getAllRating() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Rating getRating(String ratingId) {
		// TODO Auto-generated method stub
		return repo.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException("Rating not found for id: " + ratingId));
	}

	public List<Rating> getRatingByUserId(String userId) {
		// TODO Auto-generated method stub
		List<Rating> ratingList = repo.findByUserId(userId);
		return ratingList;
	}

	public List<Rating> getRatingByHotelId(String hotelId) {
		// TODO Auto-generated method stub
		return repo.findByHotelId(hotelId);
	}

}
