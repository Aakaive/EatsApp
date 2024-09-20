package com.sparta.eatsapp.auth.dto.response;

import com.sparta.eatsapp.user.entity.User;
import lombok.Getter;

@Getter
public class SignupResponse {

  private final Long id;
  private final String email;

  public SignupResponse(User savedUser) {
    this.id = savedUser.getId();
    this.email = savedUser.getEmail();
  }
}
