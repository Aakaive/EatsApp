package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.ResponseRestaurantDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public ResponseRestaurantDto createRestaurant(RestaurantRequestDto requestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(requestDto.getRestaurantName());
        restaurant.setClosingTime(requestDto.getClosingTime());
        restaurant.setOpeningTime(requestDto.getOpeningTime());
        restaurant.setMinimumPrice(requestDto.getMinimumPrice());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return new ResponseRestaurantDto(savedRestaurant);
    }

    public ResponseRestaurantDto updateRestaurant(RestaurantRequestDto requestDto) {
        return null;
    }
}
