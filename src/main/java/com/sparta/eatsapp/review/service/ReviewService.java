package com.sparta.eatsapp.review.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import com.sparta.eatsapp.order.service.OrderService;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import com.sparta.eatsapp.review.dto.ReviewResponseDto;
import com.sparta.eatsapp.review.entity.Review;
import com.sparta.eatsapp.review.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    public ReviewResponseDto save(ReviewRequestDto requestDto, AuthUser authUser) {

        Order order = findByOrderId(requestDto.getOrderId());

        if(!order.getUser().getId().equals(authUser.getId())) {
            throw new IllegalArgumentException("자신의 주문 건에만 리뷰를 작성할 수 있습니다.");
        }

        // 주문 건에 대한 리뷰 작성 여부 확인
        if(reviewRepository.findByOrder(order).isPresent()) {
            throw new IllegalArgumentException("리뷰를 이미 작성하셨습니다.");
        }

        if(!order.getOrderStatus().equals(OrderStatus.FINISH)) {
            throw new IllegalArgumentException("배달이 완료된 후에 리뷰를 작성할 수 있습니다.");
        }

        Review review = new Review(requestDto, order);
        order.saveReview(review);
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(savedReview);
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
            reviewLists.add(new ReviewResponseDto(reviewList));
        }

        return reviewLists;
    }

    @Transactional
    public ReviewResponseDto modifiedReview(Long reviewId, ReviewRequestDto requestDto, AuthUser authUser) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("유효한 리뷰가 아닙니다."));

        if(!review.getOrder().getUser().getId().equals(authUser.getId())) {
            throw new IllegalArgumentException("자신의 리뷰만 수정 할 수 있습니다.");
        }

        review.modifiedReview(requestDto);

        return new ReviewResponseDto(review);
    }

    public Order findByOrderId(Long orderId){
        return orderService.findByOrderId(orderId);
    }
}
