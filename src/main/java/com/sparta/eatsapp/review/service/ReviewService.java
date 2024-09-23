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

        // 주문 건에 대한 리뷰 작성 여부 확인
        if(reviewRepository.findByOrderId(requestDto.getOrderId()).isPresent()) {
            throw new IllegalArgumentException("리뷰를 이미 작성하셨습니다.");
        }

        Order order = findByOrderId(requestDto.getOrderId());

        if(!order.getOrderStatus().equals(OrderStatus.FINISH)) {
            throw new IllegalArgumentException("배달이 완료된 후에 리뷰를 작성할 수 있습니다.");
        }

        Review review = new Review(requestDto, order);
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(savedReview, order);
    }

    public List<ReviewResponseDto> getReviews(Long restaurantId, ReviewRequestDto requestDto) {
        int minValue = requestDto.getMin();
        int maxValue = requestDto.getMax();

        // 조회 할 리뷰의 별점 최소값
        if(minValue <= 0 || minValue > 5)
            minValue = 1;

        // 조회 할 리뷰의 별점 최대값
        if(maxValue <= 0 || maxValue > 5)
            maxValue = 5;

        // 매장의 별점 최솟값, 최댓값 기준으로 최신순으로 정렬
        List<Review> reviews = reviewRepository.findAllByRestaurantIdAndStarBetweenOrderByCreatedAtDesc(restaurantId, minValue, maxValue);

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
