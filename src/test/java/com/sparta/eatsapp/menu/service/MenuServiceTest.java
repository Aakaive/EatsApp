package com.sparta.eatsapp.menu.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.menu.dto.MenuRequestDto;
import com.sparta.eatsapp.menu.dto.MenuResponseDto;
import com.sparta.eatsapp.menu.entity.Menu;
import com.sparta.eatsapp.menu.repository.MenuRepository;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MenuService menuService;

    private AuthUser auth;
    private User user;
    private User user2;
    private Password password;
    private MenuRequestDto requestDto;
    private List<Restaurant> restaurants;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        auth = new AuthUser(1L, "aaa111@email.com");
        password = new Password("qwer1234");

        user = new User("aaa111@email.com", "Test Owner", UserRole.OWNER, "OOOOwner");
        user.setPassword(password);
        user.setDeleted(false);

        user2 = new User("bbb111@email.com", "Test Other User", UserRole.USER, "UUUUser");
        user2.setPassword(password);
        user2.setDeleted(false);

        restaurants = new ArrayList<>();

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setStatus(true);
        restaurant1.setOwner(user);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setStatus(true);
        restaurant2.setOwner(user);

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        requestDto = new MenuRequestDto("마파두부", 12000L);
    }

    @Test
    void testCreateMenu() {
        // given
        Long id = 1L;
        when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurants.get(0)));
        when(menuRepository.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // when
        MenuResponseDto menuResponseDto = menuService.createMenu(auth, requestDto, id);

        // then
        assertNotNull(menuResponseDto);
        assertEquals("마파두부", menuResponseDto.getMenuName());
        verify(menuRepository).save(any(Menu.class));
    }
}
