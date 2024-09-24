package com.sparta.eatsapp.review.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import com.sparta.eatsapp.review.dto.ReviewResponseDto;
import com.sparta.eatsapp.review.entity.Review;
import com.sparta.eatsapp.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/review")
    public ResponseEntity<ReviewResponseDto> save(@RequestBody ReviewRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.save(requestDto, authUser));
    }

    // 해당 매장의 모든 리뷰 조회
    @GetMapping("/restaurant/{restaurantId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@PathVariable Long restaurantId, @RequestBody ReviewRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.getReviews(restaurantId, requestDto));
    }

    // 리뷰 수정
    @PatchMapping("/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> modifiedReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.ok(reviewService.modifiedReview(reviewId, requestDto, authUser));
    }
}
