package com.sparta.eatsapp.review.service;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.service.OrderService;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import com.sparta.eatsapp.review.dto.ReviewResponseDto;
import com.sparta.eatsapp.review.entity.Review;
import com.sparta.eatsapp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewReqository;
    private final OrderService orderService;

    public ReviewResponseDto save(ReviewRequestDto requestDto) {
        Order order = findByOrderId(requestDto.getOrderId());
        Review review = new Review(requestDto, order);
        Review savedReview = reviewReqository.save(review);

        return new ReviewResponseDto(savedReview, order);
    }

    public List<ReviewResponseDto> getReviews(Long marketId) {
        List<Review> reviews = reviewReqository.findAllByRestaurantId(marketId);

        List<ReviewResponseDto> reviewLists = new ArrayList<>();
        for(Review reviewList : reviews){
            Order order = findByOrderId(reviewList.getOrderId());
            reviewLists.add(new ReviewResponseDto(reviewList, order));
        }

        return reviewLists;
    }

    @Transactional
    public ReviewResponseDto modifiedReview(Long reviewId, ReviewRequestDto requestDto) {
        Review review = reviewReqository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("유효한 리뷰가 아닙니다."));

        Order order = findByOrderId(review.getOrderId());
        review.modifiedReview(requestDto);

        return new ReviewResponseDto(review, order);
    }

    public Order findByOrderId(Long orderId){
        return orderService.findByOrderId(orderId);
    }
}
