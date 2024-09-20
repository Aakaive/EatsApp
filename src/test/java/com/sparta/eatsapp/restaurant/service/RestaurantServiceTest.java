package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.ResponseRestaurantDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private RestaurantRequestDto restaurantRequestDto;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantRequestDto = new RestaurantRequestDto("Test Restaurant", 10000L, LocalTime.of(9, 0), LocalTime.of(22, 0));
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setMinimumPrice(10000L);
        restaurant.setOpeningTime(LocalTime.of(9, 0));
        restaurant.setClosingTime(LocalTime.of(22, 0));
        restaurant.setStatus(true);
    }

    @Test
    void testCreateRestaurant() {
        // given
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        // when
        ResponseRestaurantDto response = restaurantService.createRestaurant(restaurantRequestDto);

        // then
        assertNotNull(response);
        assertEquals("Test Restaurant", response.getRestaurantName());
        assertEquals(10000L, response.getMinimumPrice());
        assertEquals(LocalTime.of(9, 0), response.getOpeningTime());
        assertEquals(LocalTime.of(22, 0), response.getClosingTime());

        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }
}
