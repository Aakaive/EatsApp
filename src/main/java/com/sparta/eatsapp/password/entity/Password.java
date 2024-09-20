package com.sparta.eatsapp.password.entity;

import com.sparta.eatsapp.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Password {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 64)
  private String password;

  @Setter
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Password(String encryptedPassword) {
    this.password = encryptedPassword;
  }
}
