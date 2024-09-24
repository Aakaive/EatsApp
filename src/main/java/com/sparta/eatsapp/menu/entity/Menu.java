package com.sparta.eatsapp.menu.entity;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_name", nullable = false)
    private Restaurant restaurant;

    @Column
    private boolean active=true;
}
