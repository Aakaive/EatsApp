package com.sparta.eatsapp.user.service;

import com.sparta.eatsapp.user.dto.response.UserResponse;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserResponse getUser(Long userid) {
    //
    User user = userRepository.findById(userid).orElseThrow();
    return null;
  }
}
