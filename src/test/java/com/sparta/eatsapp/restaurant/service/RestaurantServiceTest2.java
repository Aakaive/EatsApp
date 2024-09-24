package com.sparta.eatsapp.restaurant.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.menu.dto.AllMenuResponseDto;
import com.sparta.eatsapp.menu.entity.Category;
import com.sparta.eatsapp.menu.entity.Menu;
import com.sparta.eatsapp.menu.repository.MenuRepository;
import com.sparta.eatsapp.menu.service.MenuService;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.restaurant.dto.RestaurantRequestDto;
import com.sparta.eatsapp.restaurant.dto.RestaurantResponseDto;
import com.sparta.eatsapp.restaurant.dto.ViewRestaurantResponseDto;
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

    @Mock
    MenuRepository menuRepository;

    @Mock
    MenuService menuService;

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

        password = new Password("qwer1234");

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

    @Test
    void testGetAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setStatus(true);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setStatus(false);
        Restaurant restaurant3 = new Restaurant();
        restaurant3.setStatus(true);

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);

        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> activeRestaurants = restaurantService.getAllRestaurants();

        assertNotNull(activeRestaurants);
        assertEquals(2, activeRestaurants.size());
        assertTrue(activeRestaurants.contains(restaurant1));
        assertFalse(activeRestaurants.contains(restaurant2));
        assertTrue(activeRestaurants.contains(restaurant3));
    }

    @Test
    void testGetRestaurantById() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setRestaurantName("중화중화요리요리");
        restaurant.setOwner(user);
        restaurant.setMinimumPrice(12000L);
        restaurant.setOpeningTime(LocalTime.of(9,0));
        restaurant.setClosingTime(LocalTime.of(22, 0));
        restaurant.setStatus(true);

        List<Menu> menus = addMenus(restaurant);

        List<AllMenuResponseDto> allMenuResponseDto = menus.stream().map(AllMenuResponseDto::new).toList();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuRepository.findAllByRestaurant(restaurant)).thenReturn(menus);
        when(menuService.getAllMenus(restaurantId)).thenReturn(allMenuResponseDto);

        //when
        ViewRestaurantResponseDto responseDto = restaurantService.getRestaurantById(restaurantId);
        Optional<Restaurant> restaurant1 = restaurantRepository.findById(restaurantId);
        List<Menu> menus1 = menuRepository.findAllByRestaurant(restaurant);
        List<AllMenuResponseDto> menus2 = menuService.getAllMenus(restaurantId);

        //Then
        assertNotNull(restaurant1);
        assertNotNull(restaurant);
        assertNotNull(menus1);
        assertNotNull(menus2);


        assertNotNull(responseDto);
        assertEquals("중화중화요리요리", responseDto.getRestaurantName());
        assertEquals(12000L, responseDto.getMinimumPrice());
        assertEquals(LocalTime.of(9,0), responseDto.getOpeningTime());
        assertEquals(LocalTime.of(22, 0), responseDto.getClosingTime());
        verify(menuRepository).findAllByRestaurant(restaurant);
    }

    public List<Menu> addMenus(Restaurant restaurant){
        List<Menu> menus = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setId(1L);
        menu1.setName("마파두부");
        menu1.setPrice(12000L);
        menu1.setCategory(Category.Chinese);
        menu1.setRestaurant(restaurant);
        menus.add(menu1);

        Menu menu2 = new Menu();
        menu2.setId(1L);
        menu2.setName("마파두부");
        menu2.setPrice(12000L);
        menu2.setCategory(Category.Chinese);
        menu2.setRestaurant(restaurant);
        menus.add(menu2);

        Menu menu3 = new Menu();
        menu3.setId(1L);
        menu3.setName("마파두부");
        menu3.setPrice(12000L);
        menu3.setCategory(Category.Chinese);
        menu3.setRestaurant(restaurant);
        menus.add(menu3);

        Menu menu4 = new Menu();
        menu4.setId(1L);
        menu4.setName("마파두부");
        menu4.setPrice(12000L);
        menu4.setCategory(Category.Chinese);
        menu4.setRestaurant(restaurant);
        menus.add(menu4);

        return menus;
    }
}