package com.cretasom.userservice.service;

import java.util.List;

import com.cretasom.userservice.entity.User;

public interface UserService {

	User saveUser(User user);

	List<User> getAllUser();

	User getUser(String userId);

	User getUserFallback(String userId, Exception ex);
}
