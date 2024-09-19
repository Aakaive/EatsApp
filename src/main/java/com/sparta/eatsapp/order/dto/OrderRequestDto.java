package com.sparta.eatsapp.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    Long marketId;
    String marketName;
    String menuName;
    int price;
    String customerRequest;
    int number;
}
