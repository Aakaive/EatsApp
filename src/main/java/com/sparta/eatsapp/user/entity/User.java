package com.sparta.eatsapp.user.entity;

import com.sparta.eatsapp.address.entity.Address;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.user.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 254)
  private String email;
  @Enumerated(EnumType.STRING)
  private UserRole role;
  private int market_count;
  private String name;
  private String nickname;
  @Column(columnDefinition = "TINYINT(1)")
  private boolean is_deleted;

  @Setter
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Password password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Map<String, Address> addresses = new HashMap<>();

  public User(String email, String name, UserRole role,
      String nickname) {
    this.email = email;
    this.name = name;
    this.role = role;
    this.nickname = nickname;
  }

  public void addAddresses(Address address) {
    this.addresses.put(address.getLocation(), address);
    address.setUser(this);
  }

  public void update(String address, String nickname, String location) {
    this.nickname = nickname;
    this.getAddresses().get(location).setAddress(address);
  }

  public void updateNickname(String address) {
    this.nickname = nickname;
  }
}
