package com.cretasom.ratingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.cretasom.ratingservice.repo.UserRepository;

@SpringBootApplication
public class RatingServiceApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		User user = new User();
//		User user2 = new User();
//		userRepo.save(user);
//		userRepo.save(user2);
	}
}
