package com.sparta.eatsapp.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 254)
  private String email;
  @Column(length = 64)
  private String password;
  private String role;
  private int market_count;
  private String name;
  private String nickname;

  @Column(columnDefinition = "TINYINT(1)")
  private boolean is_deleted;

}
