package com.cretasom.hms.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	private String userId;
	private String name;
	private String email;
	private String about;
	@Transient
	private List<Rating> ratingList;
}
