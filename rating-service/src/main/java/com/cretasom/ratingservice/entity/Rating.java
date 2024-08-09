package com.cretasom.ratingservice.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cretasom.ratingservice.entity.external.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data

@Document("rating")
public class Rating {
	@Id
	private String id;
	@Transient
	private String userId;
	@JsonIgnore
	@DBRef
	private User user;
	private String hotelId;
	private int rating;
	private String feedback;
}
