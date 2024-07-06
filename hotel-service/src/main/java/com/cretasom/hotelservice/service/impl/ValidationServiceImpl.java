package com.cretasom.hotelservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cretasom.hotelservice.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public int validateToken(String token) {
		String uri = "http://HOTEL-SECURITY/auth/validate/" + token;
		ResponseEntity<Integer> resp = restTemplate.getForEntity(uri, Integer.class);
		return resp.getBody();

	}

}
