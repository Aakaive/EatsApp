package com.sparta.eatsapp.restaurant.repository;

import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByOwner(User user);
}
