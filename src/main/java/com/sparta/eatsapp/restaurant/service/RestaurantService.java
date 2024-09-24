package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.menu.dto.AllMenuResponseDto;
import com.sparta.eatsapp.menu.service.MenuService;
import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantResponseDto;
import com.sparta.eatsapp.restaurant.dto.ViewRestaurantResponseDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import com.sparta.eatsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuService menuService;

    public RestaurantResponseDto createRestaurant(AuthUser auth, RestaurantRequestDto requestDto) {
        User user = userRepository.findById(auth.getId()).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        List<Restaurant> restaurants = restaurantRepository.findAllByOwner(user);

        long count = restaurants.stream().filter(restaurant -> restaurant.isStatus()).count();

        if(count >= 3){
            throw new IllegalArgumentException("The maximum number of restaurants that can be registered has been exceeded.");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(user);
        restaurant.setRestaurantName(requestDto.getRestaurantName());
        restaurant.setClosingTime(requestDto.getClosingTime());
        restaurant.setOpeningTime(requestDto.getOpeningTime());
        restaurant.setMinimumPrice(requestDto.getMinimumPrice());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return new RestaurantResponseDto(savedRestaurant);
    }

    public RestaurantResponseDto updateRestaurant(AuthUser auth, RestaurantRequestDto requestDto, Long id) {
        User user = userRepository.findById(auth.getId()).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        Restaurant restaurant = findMyRestaurant(auth, id);

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

    public Long deleteRestaurant(AuthUser auth, Long id) {
        Restaurant restaurant = findMyRestaurant(auth, id);
        restaurant.setStatus(false);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    public Restaurant findMyRestaurant(AuthUser auth, Long id) {
        User user = userRepository.findById(auth.getId()).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        if(!user.getRole().equals(UserRole.OWNER)){
            new IllegalArgumentException("You are not the owner");
        }
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Restaurant not found")
        );

        if(!restaurant.getOwner().equals(user)){
            new IllegalArgumentException("You are not the owner of this restaurant");
        }
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().filter(Restaurant::isStatus).toList();
    }

    public ViewRestaurantResponseDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("Restaurant not found")
        );

        List<AllMenuResponseDto> allMenuResponseDtos = menuService.getAllMenus(restaurant.getId());

        return new ViewRestaurantResponseDto(restaurant, allMenuResponseDtos);
    }
}
