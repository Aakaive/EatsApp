package com.sparta.eatsapp.review.dto;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private String userName;
    private String menuName;
    private int star;
    private String content;
    private LocalDateTime createdAt;

    public ReviewResponseDto(Review review, Order order) {
        this.menuName = order.getMenuName();
        this.star = review.getStar();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}