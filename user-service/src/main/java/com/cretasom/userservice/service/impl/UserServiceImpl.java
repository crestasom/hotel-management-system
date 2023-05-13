package com.cretasom.userservice.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cretasom.hms.exception.ResourceNotFoundException;

import com.cretasom.userservice.entity.Rating;
import com.cretasom.userservice.entity.User;
import com.cretasom.userservice.repo.UserRepository;
import com.cretasom.userservice.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private final WebClient.Builder webClientBuilder;
	private UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
		Rating[] ratingResponses = webClientBuilder.build().get().uri("http://rating-service/rating/user/" + userId)
				.retrieve().bodyToMono(Rating[].class).block();
		logger.info("inventoryResponses [{}]", ratingResponses[0]);
		user.setRatingList(Arrays.asList(ratingResponses));

		return user;
	}

}
