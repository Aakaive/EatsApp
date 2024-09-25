package com.sparta.eatsapp.menu.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.menu.dto.AllMenuResponseDto;
import com.sparta.eatsapp.menu.dto.MenuRequestDto;
import com.sparta.eatsapp.menu.dto.MenuResponseDto;
import com.sparta.eatsapp.menu.entity.Category;
import com.sparta.eatsapp.menu.repository.MenuRepository;
import com.sparta.eatsapp.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/eats/menu")
public class MenuController {

    private final MenuService menuService;
    private final MenuRepository menuRepository;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{id}")
    public ResponseEntity<MenuResponseDto> createMenu(@Auth AuthUser auth, @RequestBody MenuRequestDto menuRequestDto, @PathVariable(name = "id") Long restaurantId) {
        MenuResponseDto menuResponseDto = menuService.createMenu(auth, menuRequestDto, restaurantId);

        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@Auth AuthUser auth, @RequestBody MenuRequestDto menuRequestDto, @PathVariable(name = "id") Long menuId) {
        MenuResponseDto menuResponseDto = menuService.updateMenu(auth, menuRequestDto, menuId);

        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMenu(@Auth AuthUser auth, @PathVariable(name = "id") Long menuId) {
        Long deletedMenuId = menuService.deleteMenu(auth, menuId);

        return new ResponseEntity<>(deletedMenuId, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<AllMenuResponseDto>> getAllMenusByCategory(@RequestParam Category category) {
        List<AllMenuResponseDto> menus = menuService.getAllMenusByCategory(category);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}
