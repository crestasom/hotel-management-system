package com.cretasom.ratingservice.repo;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.cretasom.ratingservice.entity.Rating;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {

	List<Rating> findByUserId(String userId);

	Optional<Rating> findByRatingId(String ratingId);

	List<Rating> findByHotelId(String hotelId);

}
