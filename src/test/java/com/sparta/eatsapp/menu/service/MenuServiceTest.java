package com.sparta.eatsapp.menu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.menu.dto.AllMenuResponseDto;
import com.sparta.eatsapp.menu.dto.MenuRequestDto;
import com.sparta.eatsapp.menu.dto.MenuResponseDto;
import com.sparta.eatsapp.menu.entity.Category;
import com.sparta.eatsapp.menu.entity.Menu;
import com.sparta.eatsapp.menu.repository.MenuRepository;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import com.sparta.eatsapp.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    auth = new AuthUser(1L, "aaa111@email.com", UserRole.USER, null);
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

    requestDto = new MenuRequestDto("마파두부", 12000L, Category.Chinese);
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
    assertEquals(Category.Chinese, menuResponseDto.getCategory());
    verify(menuRepository).save(any(Menu.class));
  }

  @Test
  void testUpdateMenu() {
    Long id = 1L;
    Menu menu = new Menu();
    menu.setId(id);
    menu.setName("알리오올리오");
    menu.setPrice(16000L);
    menu.setCategory(Category.Western);
    menu.setRestaurant(restaurants.get(0));

    when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));
    when(menuRepository.findById(id)).thenReturn(Optional.of(menu));
    when(menuRepository.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));

    MenuResponseDto menuResponseDto = menuService.updateMenu(auth, requestDto, id);
    assertNotNull(menuResponseDto);
    assertNotEquals("알리오올리오", menuResponseDto.getMenuName());
    assertEquals(Category.Chinese, menuResponseDto.getCategory());
    verify(menuRepository).save(any(Menu.class));
  }

  @Test
  void testDeleteMenu() {
    Long id = 1L;
    Menu menu = new Menu();
    menu.setId(id);
    menu.setActive(true);
    menu.setRestaurant(restaurants.get(0));
    when(userRepository.findById(auth.getId())).thenReturn(Optional.of(user));
    when(menuRepository.findById(id)).thenReturn(Optional.of(menu));
    when(menuRepository.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));

    menuService.deleteMenu(auth, id);
    assertFalse(menu.isActive());
    verify(menuRepository).save(any(Menu.class));

  }

  @Test
  void testGetAllMenusByCategory() {
    Long userId = 1L;
    Category category1 = Category.Western;
    Category category2 = Category.Chinese;
    List<Menu> menus = addMenus(restaurants.get(0));
    List<Menu> menusFiltered = menus.stream().filter(menu -> menu.getCategory().equals(category2))
        .toList();
    List<AllMenuResponseDto> menusFilteredDto = menusFiltered.stream().map(AllMenuResponseDto::new)
        .collect(Collectors.toList());
    when(menuRepository.findAllByCategory(category2)).thenReturn(menusFiltered);

    //when
    List<AllMenuResponseDto> menusByCategory = menuService.getAllMenusByCategory(category2);

    // then
    assertNotNull(menusByCategory);
    for (int i = 0; i < menusFilteredDto.size(); i++) {
      assertEquals(menusFilteredDto.get(i).getMenuId(), menusByCategory.get(i).getMenuId());
      assertEquals(menusFilteredDto.get(i).getMenuName(), menusByCategory.get(i).getMenuName());
      assertEquals(menusFilteredDto.get(i).getPrice(), menusByCategory.get(i).getPrice());
    }
    verify(menuRepository).findAllByCategory(category2);
  }

  public List<Menu> addMenus(Restaurant restaurant) {
    List<Menu> menus = new ArrayList<>();
    Menu menu1 = new Menu();
    menu1.setId(1L);
    menu1.setName("마파두부");
    menu1.setPrice(12000L);
    menu1.setCategory(Category.Chinese);
    menu1.setRestaurant(restaurant);
    menus.add(menu1);

    Menu menu2 = new Menu();
    menu2.setId(2L);
    menu2.setName("스파게티");
    menu2.setPrice(16000L);
    menu2.setCategory(Category.Western);
    menu2.setRestaurant(restaurant);
    menus.add(menu2);

    Menu menu3 = new Menu();
    menu3.setId(3L);
    menu3.setName("짜장면");
    menu3.setPrice(8000L);
    menu3.setCategory(Category.Chinese);
    menu3.setRestaurant(restaurant);
    menus.add(menu3);

    Menu menu4 = new Menu();
    menu4.setId(4L);
    menu4.setName("아메리카노");
    menu4.setPrice(1800L);
    menu4.setCategory(Category.Dessert);
    menu4.setRestaurant(restaurant);
    menus.add(menu4);

    return menus;
  }
}
