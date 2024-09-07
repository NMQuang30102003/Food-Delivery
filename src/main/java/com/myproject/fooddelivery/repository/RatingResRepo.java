package com.myproject.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.fooddelivery.Entity.rating_restaurant;

public interface RatingResRepo extends JpaRepository<rating_restaurant,Integer> {
}
