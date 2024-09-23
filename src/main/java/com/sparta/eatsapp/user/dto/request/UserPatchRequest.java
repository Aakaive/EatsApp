package com.sparta.eatsapp.user.dto.request;

import lombok.Getter;

@Getter
public class UserPatchRequest {

  private String nickname;
  private String location;
  private String address;

}
