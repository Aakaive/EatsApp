package com.sparta.eatsapp.menu.entity;

import com.sparta.eatsapp.restaurant.entity.Restaurant;
import jakarta.persistence.*;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_name", nullable = false)
    private Restaurant restaurant;
}
