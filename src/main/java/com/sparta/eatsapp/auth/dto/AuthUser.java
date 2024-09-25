package com.sparta.eatsapp.auth.dto;

import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthUser {

  private final Long id;
  private final String email;
  private final UserRole role;
  private final String password;

  public AuthUser(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.password = user.getPassword().getPassword();
  }
}