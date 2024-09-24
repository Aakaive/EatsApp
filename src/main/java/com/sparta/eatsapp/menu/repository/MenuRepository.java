package com.sparta.eatsapp.menu.repository;

import com.sparta.eatsapp.menu.entity.Category;
import com.sparta.eatsapp.menu.entity.Menu;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurant(Restaurant restaurant);

    List<Menu> findAllByCategory(Category category);
}
