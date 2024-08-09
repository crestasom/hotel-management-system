package com.cretasom.userservice.controller;

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

import com.cretasom.userservice.entity.User;
import com.cretasom.userservice.service.UserService;
import com.cretasom.userservice.service.impl.UserServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({ "/users", "/staff" })
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));

	}

	@GetMapping("/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")

	public ResponseEntity<User> getUser(@PathVariable String userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
	}

	public ResponseEntity<User> ratingHotelFallback(@PathVariable String userId, Exception ex) {
		log.error("Fall back method es executing because service is down:", ex);

		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserFallback(userId, ex));

	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
	}
}
