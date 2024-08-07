package com.cretasom.userservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cretasom.hms.exception.ResourceNotFoundException;
import com.cretasom.userservice.entity.Hotel;
import com.cretasom.userservice.entity.Rating;
import com.cretasom.userservice.entity.User;
import com.cretasom.userservice.exception.HotelResourceNotFoundException;
import com.cretasom.userservice.exception.RatingsNotFoundException;
import com.cretasom.userservice.repo.UserRepository;
import com.cretasom.userservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
//	private final HotelService hotelService;
////	private final RatingService ratingService;
	@Autowired
	private RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		user.setUserId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	/**
	 * getAllUser this method returns all users for database
	 * 
	 * @return List<User> list of users we get from db
	 */
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();

	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Given ID is not found in server: " + userId));
		// Using rest template to fetch objects from rating service
		String ratingUri = "http://rating-service/rating/user/" + user.getUserId();
		String hotelUri = "http://hotel-service/hotel/";
//		String ratingUri = "http://localhost:8088/rating/user/" + user.getUserId();
//		String hotelUri = "http://localhost:8098/hotel/";
		List<Rating> ratingList = null;
		try {
			log.info("Calling rating service to get rating");

			ratingList = restTemplate.exchange(ratingUri, HttpMethod.GET, getAuthHeader(),
					new ParameterizedTypeReference<List<Rating>>() {
					}).getBody();
		} catch (Exception ex) {
			throw new RatingsNotFoundException(ex.getMessage());
		}

		log.info("ratingList [{}]", ratingList);
		try {
			if (ratingList != null) {
				for (Rating r : ratingList) {
					log.info("hotelId [{}]" + r.getHotelId());

					log.info("Calling rating service to get rating");
					ResponseEntity<Hotel> h = restTemplate.exchange(hotelUri + r.getHotelId(), HttpMethod.GET,
							getAuthHeader(), Hotel.class);

					r.setHotel(h.getBody());
				}
			}
		} catch (Exception ex) {
			throw new HotelResourceNotFoundException(ex.getMessage());
		}

		user.setRatingList(ratingList);
		// Using feign client
//		List<Rating> ratingList = ratingService.getAllRatingByUserId(userId);
//		user.setRatingList(ratingList.stream().map(rating -> {
//			Hotel hotel = hotelService.getHotel(rating.getHotelId());
//			logger.info("hotel [{}]", hotel);
//			rating.setHotel(hotel);
//			return rating;
//		}).collect(Collectors.toList()));

		return user;
	}

	@Override
	public User getUserFallback(String userId, Exception ex) {
		// TODO Auto-generated method stub
		if (ex instanceof RatingsNotFoundException) {
			return getUserOnly(userId);
		} else if (ex instanceof HotelResourceNotFoundException) {
			return getUserWithRatingsOnly(userId);
		}
		return User.builder().name("test").email("test@gmail.com").build();
	}

	private User getUserOnly(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Given ID is not found in server: " + userId));
		return user;
	}

	private User getUserWithRatingsOnly(String userId) {
		// TODO Auto-generated method stub
		log.info("getUserWithRatingsOnly");
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Given ID is not found in server: " + userId));

		// Using rest template to fetch objects from rating service
		String ratingUri = "http://rating-service/rating/user/" + user.getUserId();
//		String ratingUri = "http://localhost:8088/rating/user/" + user.getUserId();
//		String hotelUri = "http://localhost:8098/hotel/";
		List<Rating> ratingList = null;
		try {
			log.info("Calling rating service to get rating");

			ratingList = restTemplate.exchange(ratingUri, HttpMethod.GET, getAuthHeader(),
					new ParameterizedTypeReference<List<Rating>>() {
					}).getBody();
		} catch (Exception ex) {
			throw new RatingsNotFoundException(ex.getMessage());
		}

		user.setRatingList(ratingList);

		return user;
	}

	public static HttpEntity<String> getAuthHeader() {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
		if (request == null) {
			return null;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", request.getHeader("Authorization"));

		// Create HttpEntity with the headers
		return new HttpEntity<>(headers);

	}
}
