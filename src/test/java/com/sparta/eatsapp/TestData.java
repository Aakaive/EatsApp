package com.sparta.eatsapp;

import com.sparta.eatsapp.auth.dto.request.SignupRequest;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;

public class TestData {

  public static SignupRequest signupRequest = SignupRequest.builder()
      .email("dnwls111@naver.com")
      .password("dnwls111")
      .name("김우진")
      .address("서울특별시")
      .location("집")
      .role("USER")
      .nickname("testNick")
      .build();
  public static User testUser = new User(
      signupRequest.getEmail(),
      signupRequest.getName(),
      UserRole.of(signupRequest.getRole()),
      signupRequest.getNickname()
  );
  public static Password testPassword = new Password("dnwls111");
}
