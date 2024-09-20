package com.sparta.eatsapp.address.entity;

import com.sparta.eatsapp.user.entity.User;
import jakarta.persistence.Entity;
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
  private Long id;
  private String address;
  private String location;

  @ManyToOne
  @Setter
  @JoinColumn(name = "user_id")
  private User user;

  public Address(String address) {
    this.address = address;
  }
}
