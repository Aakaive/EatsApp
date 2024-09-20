package com.sparta.eatsapp.review.repository;

import com.sparta.eatsapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}