package com.sparta.eatsapp.review.repository;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByRestaurantIdAndStarBetweenOrderByCreatedAtDesc(Long restaurantId, int min, int max);

    Optional<Review> findByOrder(Order order);
}