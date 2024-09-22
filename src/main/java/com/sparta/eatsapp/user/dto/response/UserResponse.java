package com.sparta.eatsapp.user.dto.response;

import com.sparta.eatsapp.address.entity.Address;
import com.sparta.eatsapp.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class UserResponse {

  private String email;
  private String name;
  private List<String> address;
  private String role;
  private String nickname;

  public UserResponse(User user) {
    this.email = user.getEmail();
    this.name = user.getName();
    this.address = user.getAddresses().stream().map(Address::getAddress).toList();
    this.role = user.getRole().name();
    this.nickname = user.getNickname();
  }
}
