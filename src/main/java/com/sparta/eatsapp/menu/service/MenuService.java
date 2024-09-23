package com.sparta.eatsapp.menu.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.menu.dto.MenuRequestDto;
import com.sparta.eatsapp.menu.dto.MenuResponseDto;
import com.sparta.eatsapp.menu.entity.Menu;
import com.sparta.eatsapp.menu.repository.MenuRepository;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuResponseDto createMenu(AuthUser auth, MenuRequestDto menuRequestDto, Long restaurantId) {
        User user = userRepository.findById(auth.getId()).orElseThrow(
                () -> new IllegalArgumentException("user not found")
        );
        Menu menu = new Menu();
        menu.setName(menuRequestDto.getMenuName());
        menu.setPrice(menuRequestDto.getPrice());
        menu.setCategory(menuRequestDto.getCategory());
        menu.setRestaurant(findMyRestaurant(user, restaurantId));

        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu);
    }

    public Restaurant findMyRestaurant(User owner, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("restaurant not found")
        );
        if(!owner.equals(restaurant.getOwner())) {
            throw new IllegalArgumentException("owner is not the owner of the restaurant");
        }
        return restaurant;
    }
}
