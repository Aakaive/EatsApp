package com.sparta.eatsapp.review.service;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import com.sparta.eatsapp.order.service.OrderService;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import com.sparta.eatsapp.review.dto.ReviewResponseDto;
import com.sparta.eatsapp.review.entity.Review;
import com.sparta.eatsapp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    public ReviewResponseDto save(ReviewRequestDto requestDto) {
        Order order = findByOrderId(requestDto.getOrderId());

        if(!order.getOrderStatus().equals(OrderStatus.FINISH)) {
            throw new IllegalArgumentException("배달이 완료된 후에 리뷰를 작성할 수 있습니다.");
        }

        Review review = new Review(requestDto, order);
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(savedReview, order);
    }

    public List<ReviewResponseDto> getReviews(Long marketId) {
        List<Review> reviews = reviewRepository.findAllByRestaurantId(marketId);

        List<ReviewResponseDto> reviewLists = new ArrayList<>();
        for(Review reviewList : reviews){
            Order order = findByOrderId(reviewList.getOrderId());
            reviewLists.add(new ReviewResponseDto(reviewList, order));
        }

        return reviewLists;
    }

    @Transactional
    public ReviewResponseDto modifiedReview(Long reviewId, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("유효한 리뷰가 아닙니다."));

        Order order = findByOrderId(review.getOrderId());
        review.modifiedReview(requestDto);

        return new ReviewResponseDto(review, order);
    }

    public Order findByOrderId(Long orderId){
        return orderService.findByOrderId(orderId);
    }
}
