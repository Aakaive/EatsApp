package com.sparta.eatsapp.menu.repository;

import com.sparta.eatsapp.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
