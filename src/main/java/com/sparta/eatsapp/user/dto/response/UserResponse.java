package com.sparta.eatsapp.user.dto.response;

import com.sparta.eatsapp.address.entity.Address;
import com.sparta.eatsapp.user.entity.User;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class UserResponse {

  private String email;
  private String name;
  private Map<String, String> address;
  private String role;
  private String nickname;

  public UserResponse(User user) {
    this.email = user.getEmail();
    this.name = user.getName();
    this.address = user.getAddresses().values().stream()
        .collect(Collectors.toMap(Address::getLocation, Address::getAddress));
    this.role = user.getRole().name();
    this.nickname = user.getNickname();
  }
}
