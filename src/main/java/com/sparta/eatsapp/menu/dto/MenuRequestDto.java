package com.sparta.eatsapp.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRequestDto {
    private String menuName;
    private Long price;
}
