package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantResponseDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantResponseDto createRestaurant(RestaurantRequestDto requestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(requestDto.getRestaurantName());
        restaurant.setClosingTime(requestDto.getClosingTime());
        restaurant.setOpeningTime(requestDto.getOpeningTime());
        restaurant.setMinimumPrice(requestDto.getMinimumPrice());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return new RestaurantResponseDto(savedRestaurant);
    }

    public RestaurantResponseDto updateRestaurant(RestaurantRequestDto requestDto) {
        return null;
    }
}
