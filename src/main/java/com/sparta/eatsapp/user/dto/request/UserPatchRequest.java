package com.sparta.eatsapp.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPatchRequest {

  private String password;
  private String nickname;
  private String location;
  private String address;

}
