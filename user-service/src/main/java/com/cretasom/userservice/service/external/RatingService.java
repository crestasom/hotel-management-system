package com.cretasom.userservice.service.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cretasom.userservice.entity.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
	@GetMapping("/rating/user/{userId}")
	List<Rating> getAllRatingByUserId(@PathVariable String userId);
}
