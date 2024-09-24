package com.sparta.eatsapp.menu.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.menu.dto.MenuRequestDto;
import com.sparta.eatsapp.menu.dto.MenuResponseDto;
import com.sparta.eatsapp.menu.repository.MenuRepository;
import com.sparta.eatsapp.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eats/menu")
public class MenuController {

    private final MenuService menuService;
    private final MenuRepository menuRepository;

    @PostMapping("/{id}")
    public ResponseEntity<MenuResponseDto> createMenu(@Auth AuthUser auth, @RequestBody MenuRequestDto menuRequestDto, @PathVariable Long restaurantId) {
        MenuResponseDto menuResponseDto = menuService.createMenu(auth, menuRequestDto, restaurantId);

        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@Auth AuthUser auth, @RequestBody MenuRequestDto menuRequestDto, @PathVariable Long menuId) {
        MenuResponseDto menuResponseDto = menuService.updateMenu(auth, menuRequestDto, menuId);

        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMenu(@Auth AuthUser auth, @PathVariable Long menuId) {
        Long deletedMenuId = menuService.deleteMenu(auth, menuId);

        return new ResponseEntity<>(deletedMenuId, HttpStatus.OK);
    }
}
