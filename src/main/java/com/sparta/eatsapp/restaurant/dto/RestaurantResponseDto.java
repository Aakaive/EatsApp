package com.sparta.eatsapp.restaurant.dto;

import com.sparta.eatsapp.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ResponseRestaurantDto {
    private Long id;
    private String restaurantName;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Long minimumPrice;
    private boolean status;

    public ResponseRestaurantDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.openingTime = restaurant.getOpeningTime();
        this.closingTime = restaurant.getClosingTime();
        this.minimumPrice = restaurant.getMinimumPrice();
        this.status = restaurant.isStatus();
    }
}
