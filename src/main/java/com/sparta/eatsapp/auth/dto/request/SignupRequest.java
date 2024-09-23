package com.sparta.eatsapp.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequest {

  private String email;
  private String password;
  private String name;
  private String address;
  private String location;
  private String role;
  private String nickname;
}
