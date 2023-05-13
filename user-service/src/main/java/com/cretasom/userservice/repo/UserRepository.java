package com.cretasom.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cretasom.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
