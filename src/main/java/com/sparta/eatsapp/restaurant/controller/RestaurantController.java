package com.sparta.eatsapp.restaurant.controller;


import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.ResponseRestaurantDto;
import com.sparta.eatsapp.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eats")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<ResponseRestaurantDto> createRestaurant(@RequestBody RestaurantRequestDto requestDto) {
        ResponseRestaurantDto responseDto = restaurantService.createRestaurant(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseRestaurantDto> updateRestaurant(@RequestBody RestaurantRequestDto requestDto) {
        ResponseRestaurantDto responseDto = restaurantService.updateRestaurant(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
