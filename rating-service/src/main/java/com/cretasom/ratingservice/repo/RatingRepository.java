package com.cretasom.ratingservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cretasom.ratingservice.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, String> {

	List<Rating> findByUserId(String userId);

	List<Rating> findByHotelId(String hotelId);

}
