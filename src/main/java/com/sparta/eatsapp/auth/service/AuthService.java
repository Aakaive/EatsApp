package com.sparta.eatsapp.auth.service;

import com.sparta.eatsapp.auth.dto.request.SignupRequest;
import com.sparta.eatsapp.auth.dto.response.SignupResponse;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import com.sparta.eatsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  @Transactional
  public SignupResponse signup(SignupRequest signupRequest) {

    String encryptedPassword = signupRequest.getPwd();
    User user = new User(
        signupRequest.getEmail(),
        encryptedPassword,
        signupRequest.getName(),
        signupRequest.getAddress(),
        UserRole.of(signupRequest.getRole()),
        signupRequest.getNickname()
    );
    User savedUser = userRepository.save(user);
    return new SignupResponse(savedUser);
  }
}
