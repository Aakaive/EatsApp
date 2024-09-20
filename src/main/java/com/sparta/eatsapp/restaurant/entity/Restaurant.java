package com.sparta.eatsapp.restaurant.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column
    private Long minimumPrice;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

    @Column
    private LocalTime openingTime;

    @Column
    private LocalTime closingTime;

    @Column(nullable = false)
    boolean status = true;
}
