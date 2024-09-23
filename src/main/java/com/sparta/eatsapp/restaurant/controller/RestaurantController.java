package com.sparta.eatsapp.restaurant.controller;


import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.config.AuthUserArgumentResolver;
import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantResponseDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantsResponseDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eats")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@Auth AuthUser auth, @RequestBody RestaurantRequestDto requestDto) {
        RestaurantResponseDto responseDto = restaurantService.createRestaurant(auth, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@Auth AuthUser auth, @RequestBody RestaurantRequestDto requestDto, @PathVariable Long id) {
        RestaurantResponseDto responseDto = restaurantService.updateRestaurant(auth, requestDto, id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRestaurant(@Auth AuthUser auth, @PathVariable Long id) {
        Long responseId = restaurantService.deleteRestaurant(auth, id);
        return new ResponseEntity<>(responseId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantsResponseDto>> getAllRestaurants() {
        // 모든 레스토랑을 가져옴
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        // Restaurant 엔티티를 RestaurantResponseDto로 변환
        List<RestaurantsResponseDto> responseDtos = restaurants.stream()
                .map(RestaurantsResponseDto::new)
                .toList();

        // 응답을 반환
        return ResponseEntity.ok(responseDtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        RestaurantResponseDto responseDto = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
