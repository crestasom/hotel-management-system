package com.cretasom.ratingservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cretasom.ratingservice.entity.external.User;

public interface UserRepository extends MongoRepository<User, String> {

}
