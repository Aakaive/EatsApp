package com.sparta.eatsapp.restaurant.dto;

import com.sparta.eatsapp.menu.dto.MenuResponseDtos;
import com.sparta.eatsapp.menu.entity.Menu;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ViewRestaurantResponseDto {
    private Long id;
    private String restaurantName;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Long minimumPrice;
    private boolean status;
    private List<MenuResponseDtos> menuResponseDtos;

    public ViewRestaurantResponseDto(Restaurant restaurant, List<MenuResponseDtos> menuResponseDtos) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.openingTime = restaurant.getOpeningTime();
        this.closingTime = restaurant.getClosingTime();
        this.minimumPrice = restaurant.getMinimumPrice();
        this.status = restaurant.isStatus();
        this.menuResponseDtos = menuResponseDtos;
    }
}