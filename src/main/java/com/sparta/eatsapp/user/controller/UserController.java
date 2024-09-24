package com.sparta.eatsapp.user.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.user.dto.request.UserPatchRequest;
import com.sparta.eatsapp.user.dto.response.UserResponse;
import com.sparta.eatsapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/{userid}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long userid) {
    UserResponse userResponse = userService.getUser(userid);
    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }

  @PatchMapping("/{userid}")
  public ResponseEntity<UserResponse> updateUser(@Auth AuthUser authUser, @PathVariable Long userid,
      @RequestBody
      UserPatchRequest userPatchRequest) {
    UserResponse userResponse = userService.updateUser(authUser, userid, userPatchRequest);
    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }

  @DeleteMapping("/{userid}")
  public ResponseEntity<Long> deleteUser(@Auth AuthUser authUser, @PathVariable Long userid) {
    Long userId = userService.deleteUser(userid, authUser);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(userId);
  }
}
