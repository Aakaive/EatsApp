package com.sparta.eatsapp.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long orderId;
    private String content;
    private int star;
}
