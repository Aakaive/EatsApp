package com.sparta.eatsapp.address.entity;

import com.sparta.eatsapp.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Setter
  private String address;
  @Setter
  private String location;

  @ManyToOne
  @Setter
  @JoinColumn(name = "user_id")
  private User user;

  public Address(String address, String location) {
    this.address = address;
    this.location = location;
  }
}
