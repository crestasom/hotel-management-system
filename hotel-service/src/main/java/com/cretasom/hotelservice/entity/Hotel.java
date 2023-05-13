package com.cretasom.hotelservice.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "hotel")
@Data
public class Hotel {
	@Id
	private String hotelId;
	private String name;
	private String location;
	private String about;
	@Transient
	private List<Rating> ratingList;
}
