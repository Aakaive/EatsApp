package com.sparta.eatsapp.menu.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.menu.dto.MenuRequestDto;
import com.sparta.eatsapp.menu.dto.MenuResponseDto;
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

    @PostMapping("/{id}")
    public ResponseEntity<MenuResponseDto> createMenu(@Auth AuthUser auth, @RequestBody MenuRequestDto menuRequestDto, @PathVariable Long restaurnatId) {
        MenuResponseDto menuResponseDto = menuService.createMenu(auth, menuRequestDto, restaurnatId);

        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }
}
