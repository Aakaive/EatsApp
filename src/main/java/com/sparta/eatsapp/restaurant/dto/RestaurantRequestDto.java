package com.sparta.eatsapp.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {
    private String restaurantName;
    private Long minimumPrice;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
