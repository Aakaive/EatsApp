package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantResponseDto;
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
        RestaurantResponseDto response = restaurantService.createRestaurant(restaurantRequestDto);

        // then
        assertNotNull(response);
        assertEquals("Test Restaurant", response.getRestaurantName());
        assertEquals(10000L, response.getMinimumPrice());
        assertEquals(LocalTime.of(9, 0), response.getOpeningTime());
        assertEquals(LocalTime.of(22, 0), response.getClosingTime());

        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant() {
        RestaurantRequestDto requestDto = new RestaurantRequestDto();
        requestDto.setRestaurantName("modified name");
        long id = restaurant.getId();



        // given
        when(restaurantRepository.findById(id)).thenReturn(java.util.Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);  // save 이후 업데이트된 값을 반환

        // when
        RestaurantResponseDto response = restaurantService.updateRestaurant(requestDto, id);

        // then
        assertNotNull(response);
        assertEquals("modified name", response.getRestaurantName());

        verify(restaurantRepository, times(1)).findById(id);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testDeleteRestaurant() {
        // given
        long id = restaurant.getId();

        when(restaurantRepository.findById(id)).thenReturn(java.util.Optional.of(restaurant));

        // when
        Long deletedRestaurantId = restaurantService.deleteRestaurant(id);

        // then
        assertNotNull(deletedRestaurantId);
        assertEquals(id, deletedRestaurantId);
        assertFalse(restaurant.isStatus()); // restaurant의 상태가 false로 변경되었는지 확인

        verify(restaurantRepository, times(1)).findById(id);
        verify(restaurantRepository, times(1)).save(restaurant); // 상태 변경 후 저장되는지 확인
    }

}
