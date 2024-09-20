package com.sparta.eatsapp.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private Long restaurantId;
    private String restaurantName;
    private String menuName;
    private int price;
    private String customerRequest;
    private int number;
}
