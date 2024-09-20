package com.sparta.eatsapp.user.controller;

import com.sparta.eatsapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/{userid}")
  public ResponseEntity getUser(@PathVariable Long userid){
    userService.getUser(userid);
    return null;
  }
}
