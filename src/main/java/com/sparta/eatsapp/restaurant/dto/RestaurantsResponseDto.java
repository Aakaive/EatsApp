package com.sparta.eatsapp.restaurant.dto;

import com.sparta.eatsapp.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RestaurantsResponseDto {
    private Long id;
    private String name;

    public RestaurantsResponseDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getRestaurantName();
    }
}
