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

    public RestaurantResponseDto updateRestaurant(RestaurantRequestDto requestDto, Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException()
        );

        if (requestDto.getRestaurantName() != null) {
            restaurant.setRestaurantName(requestDto.getRestaurantName());
        }
        if (requestDto.getMinimumPrice() != null) {
            restaurant.setMinimumPrice(requestDto.getMinimumPrice());
        }
        if (requestDto.getOpeningTime() != null) {
            restaurant.setOpeningTime(requestDto.getOpeningTime());
        }
        if (requestDto.getClosingTime() != null) {
            restaurant.setClosingTime(requestDto.getClosingTime());
        }

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return new RestaurantResponseDto(updatedRestaurant);
    }
}
