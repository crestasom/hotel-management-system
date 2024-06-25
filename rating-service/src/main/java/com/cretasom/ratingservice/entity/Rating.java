package com.cretasom.ratingservice.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import jakarta.persistence.Id;

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
