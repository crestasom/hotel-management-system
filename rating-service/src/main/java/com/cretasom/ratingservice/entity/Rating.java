package com.cretasom.ratingservice.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data

@Document("rating")
public class Rating {
	@Id
	private String ratingId;
	private String userId;
	private String hotelId;
	private int rating;
	private String feedback;
}
