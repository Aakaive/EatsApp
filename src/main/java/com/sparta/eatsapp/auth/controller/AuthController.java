package com.sparta.eatsapp.auth.controller;

import com.sparta.eatsapp.auth.dto.request.SigninRequest;
import com.sparta.eatsapp.auth.dto.request.SignupRequest;
import com.sparta.eatsapp.auth.dto.response.SignupResponse;
import com.sparta.eatsapp.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
    SignupResponse signupResponse = authService.signup(signupRequest);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(signupResponse);
  }

  @PostMapping("/signin")
  public ResponseEntity<String> signin(@RequestBody SigninRequest signinRequest) {
    String token = authService.signin(signinRequest);
    return ResponseEntity.status(HttpStatus.OK).body(token);
  }
}
