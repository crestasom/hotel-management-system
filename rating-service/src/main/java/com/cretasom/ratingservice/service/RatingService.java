package com.cretasom.ratingservice.service;

import java.util.List;

import com.cretasom.ratingservice.entity.Rating;

public interface RatingService {

	Rating saveRating(Rating rating);

	List<Rating> getAllRating();

	Rating getRating(String ratingId);

	List<Rating> getRatingByUserId(String userId);

	List<Rating> getRatingByHotelId(String hotelId);
}
