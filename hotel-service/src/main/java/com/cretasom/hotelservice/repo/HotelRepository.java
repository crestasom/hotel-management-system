package com.cretasom.hotelservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cretasom.hotelservice.entity.Hotel;



public interface HotelRepository extends JpaRepository<Hotel, String> {

}
