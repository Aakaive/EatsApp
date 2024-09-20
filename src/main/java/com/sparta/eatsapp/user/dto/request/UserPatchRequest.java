package com.sparta.eatsapp.user.dto.request;

import lombok.Getter;

@Getter
public class UserPatchRequest {

  private String email;
  private String pwd;
  private String name;
  private String address;

}
