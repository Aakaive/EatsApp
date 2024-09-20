package com.sparta.eatsapp.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
  private String address;

  @Column(columnDefinition = "TINYINT(1)")
  private boolean is_deleted;

  public User(String email, String encryptedPassword, String name, String address, String role,
      String nickname) {
    this.email = email;
    this.password = encryptedPassword;
    this.name = name;
    this.address = address;
    this.role = role;
    this.nickname = nickname;
  }

}
