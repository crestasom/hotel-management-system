package com.cretasom.userservice.controller;

import java.util.List;

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

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));

	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable String userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
	}
}
