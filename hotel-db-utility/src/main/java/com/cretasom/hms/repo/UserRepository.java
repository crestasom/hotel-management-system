package com.cretasom.hms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cretasom.hms.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
