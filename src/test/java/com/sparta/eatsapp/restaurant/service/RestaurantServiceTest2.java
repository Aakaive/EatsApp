package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantResponseDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import com.sparta.eatsapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest2 {

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RestaurantService restaurantService;

    private AuthUser auth;
    private User user;
    private Password password;
    private RestaurantRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        auth = new AuthUser(1L, "aaa111@email.com");

        Password password = new Password("qwer1234");

        user = new User("aaa111@email.com", "Test Owner", UserRole.OWNER, "testNickname");
        user.setPassword(password);
        user.setDeleted(false);

        requestDto = new RestaurantRequestDto("Test Restaurant", 10000L, LocalTime.of(9, 0), LocalTime.of(22, 0));
    }

    @Test
    void testCreateRestaurant() {
        // Given
        when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));

        List<Restaurant> restaurants = new ArrayList<>();
        when(restaurantRepository.findAllByOwner(user)).thenReturn(restaurants);

        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        RestaurantResponseDto responseDto = restaurantService.createRestaurant(auth, requestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals("Test Restaurant", responseDto.getRestaurantName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void testCreateRestaurant_whenMaximum() {
        when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));

        // 유저가 이미 3개의 음식점을 소유하고 있다고 가정
        List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setOwner(user);
            restaurant.setStatus(true);
            restaurants.add(restaurant);
        }
        when(restaurantRepository.findAllByOwner(user)).thenReturn(restaurants);

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            restaurantService.createRestaurant(auth, requestDto);
        });
        assertEquals("The maximum number of restaurants that can be registered has been exceeded.", exception.getMessage());
    }

    @Test
    void testUpdateRestaurant() {
        // given
        when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(user);
        restaurant.setStatus(true);
        restaurant.setRestaurantName("New Restaurant");
        restaurants.add(restaurant);

        when(restaurantRepository.findAllByOwner(user)).thenReturn(restaurants);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        RestaurantRequestDto updateRequestDto = new RestaurantRequestDto();
        updateRequestDto.setRestaurantName("modified name");

        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        RestaurantResponseDto updateResponseDto = restaurantService.updateRestaurant(auth, updateRequestDto, 1L);
        assertNotNull(updateResponseDto);
        assertEquals("modified name", updateResponseDto.getRestaurantName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void testDeleteRestaurant() {
        when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(user);
        restaurant.setStatus(true);
        restaurants.add(restaurant);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.findAllByOwner(user)).thenReturn(restaurants);

        restaurantService.deleteRestaurant(auth, 1L);
        assertFalse(restaurant.isStatus());
        verify(restaurantRepository).save(restaurant);
    }
}