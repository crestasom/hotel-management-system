package com.cretasom.userservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.cretasom.hms.exception.ResourceNotFoundException;
import com.cretasom.userservice.entity.Hotel;
import com.cretasom.userservice.entity.Rating;
import com.cretasom.userservice.entity.User;
import com.cretasom.userservice.repo.UserRepository;
import com.cretasom.userservice.service.UserService;
import com.cretasom.userservice.service.external.HotelService;
import com.cretasom.userservice.service.external.RatingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final HotelService hotelService;
	private final RatingService ratingService;

	public User saveUser(User user) {
		user.setUserId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public User getUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Given ID is not found in server: " + userId));

		List<Rating> ratingList = ratingService.getAllRatingByUserId(userId);
		user.setRatingList(ratingList.stream().map(rating -> {
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			logger.info("hotel [{}]", hotel);
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList()));

		return user;
	}

}
