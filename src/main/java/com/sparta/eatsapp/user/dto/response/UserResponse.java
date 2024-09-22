package com.sparta.eatsapp.user.dto.response;

import com.sparta.eatsapp.address.entity.Address;
import com.sparta.eatsapp.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UserResponse {

  private String email;
  private String name;
  private List<Address> address;
  private String role;
  private String nickname;

  public UserResponse(User user) {
    this.email = user.getEmail();
    this.name = user.getName();
    this.address = new ArrayList<>(user.getAddresses().values());
    this.role = user.getRole().name();
    this.nickname = user.getNickname();
  }
}
