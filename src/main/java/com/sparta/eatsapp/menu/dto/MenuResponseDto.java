package com.sparta.eatsapp.menu.dto;

import com.sparta.eatsapp.menu.entity.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDto {
    private Long menuId;
    private String menuName;
    private Long price;
    private Long restaurantId;

    public MenuResponseDto(Menu menu) {
        this.menuId = menu.getId();
        this.menuName = menu.getName();
        this.price = menu.getPrice();
        this.restaurantId = menu.getRestaurant().getId();
    }
}
