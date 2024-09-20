package com.sparta.eatsapp.review.service;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.service.OrderService;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import com.sparta.eatsapp.review.dto.ReviewResponseDto;
import com.sparta.eatsapp.review.entity.Review;
import com.sparta.eatsapp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewReqository;
    private final OrderService orderService;

    public ReviewResponseDto save(ReviewRequestDto requestDto) {
        Review review = new Review(requestDto);
        Review savedReview = reviewReqository.save(review);
        Order order = orderService.findByOrderId(savedReview.getOrderId());

        return new ReviewResponseDto(savedReview, order);
    }
}
