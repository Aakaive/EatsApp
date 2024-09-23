package com.sparta.eatsapp.menu.dto;

import com.sparta.eatsapp.menu.entity.Menu;

public class MenuResponseDtos {
    private Long menuId;
    private String menuName;
    private Long price;

    public MenuResponseDtos(Menu menu) {
        this.menuId = menu.getId();
        this.menuName = menu.getName();
        this.price = menu.getPrice();
    }
}
