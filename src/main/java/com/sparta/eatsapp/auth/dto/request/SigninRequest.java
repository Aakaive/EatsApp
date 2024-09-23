package com.sparta.eatsapp.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninRequest {

  private String email;
  private String password;

}
