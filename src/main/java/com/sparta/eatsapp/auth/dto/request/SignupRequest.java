package com.sparta.eatsapp.auth.dto.request;

import lombok.Getter;

@Getter
public class SignupRequest {

  private String email;
  private String pwd;
  private String name;
  private String address;
  private String location;
  private String role;
  private String nickname;
}
