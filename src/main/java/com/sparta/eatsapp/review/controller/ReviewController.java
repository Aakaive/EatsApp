package com.sparta.eatsapp.review.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import com.sparta.eatsapp.review.dto.ReviewResponseDto;
import com.sparta.eatsapp.review.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> modifiedReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.ok(reviewService.modifiedReview(reviewId, requestDto, authUser));
    }
}
