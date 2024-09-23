package com.sparta.eatsapp.menu.dto;

import com.sparta.eatsapp.menu.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private String menuName;
    private Long price;
    private Category category;
}
