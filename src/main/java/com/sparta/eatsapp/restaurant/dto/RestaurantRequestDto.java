package com.sparta.eatsapp.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {
    private String restaurantName;
    private Long minimumPrice;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
