package com.sparta.eatsapp;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.auth.dto.request.SigninRequest;
import com.sparta.eatsapp.auth.dto.request.SignupRequest;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.user.dto.request.UserPatchRequest;
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

  public static SigninRequest signinRequest = SigninRequest.builder()
      .email("dnwls111@naver.com")
      .password("dnwls111")
      .build();

  public static AuthUser testAuth = new AuthUser(1L,"dnwls111@naver.com", UserRole.USER,testPassword.getPassword());

  public static UserPatchRequest userPatchRequest = UserPatchRequest.builder()
      .nickname("땅콩")
      .location("집")
      .address("제주도")
      .build();
}
