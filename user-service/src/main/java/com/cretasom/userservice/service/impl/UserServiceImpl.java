package com.cretasom.userservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cretasom.hms.exception.ResourceNotFoundException;
import com.cretasom.userservice.entity.User;
import com.cretasom.userservice.repo.UserRepository;
import com.cretasom.userservice.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public User saveUser(User user) {
		user.setUserId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public User getUser(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Given ID is not found in server: " + userId));
	}

}
