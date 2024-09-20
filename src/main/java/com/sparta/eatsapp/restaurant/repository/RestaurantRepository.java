package com.sparta.eatsapp.restaurant.repository;

import com.sparta.eatsapp.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
