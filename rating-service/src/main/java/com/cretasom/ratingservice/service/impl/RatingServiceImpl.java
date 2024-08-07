package com.cretasom.ratingservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cretasom.hms.exception.ResourceNotFoundException;
import com.cretasom.ratingservice.entity.Rating;
import com.cretasom.ratingservice.entity.external.User;
import com.cretasom.ratingservice.repo.RatingRepository;
import com.cretasom.ratingservice.repo.UserRepository;
import com.cretasom.ratingservice.service.RatingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

	private RatingRepository repo;
	private UserRepository userRepo;
	private static final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

	@Override
	public Rating saveRating(Rating rating) {
		// rating.setRatingId(UUID.randomUUID().toString());
		Optional<User> user = userRepo.findById(rating.getUserId());
		if (user.isEmpty()) {
			throw new RuntimeException("User id not exists in db");
		}
		rating.setUser(user.get());
		return repo.save(rating);
	}

	@Override
	public List<Rating> getAllRating() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Rating getRating(String ratingId) {
		// TODO Auto-generated method stub
		log.info("ratingId [{}]", ratingId);
		return repo.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException("Rating not found for id: " + ratingId));
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		// TODO Auto-generated method stub
		List<Rating> ratingList = repo.findByUserId(userId);
		return ratingList;
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		// TODO Auto-generated method stub
		return repo.findByHotelId(hotelId);
	}

}
