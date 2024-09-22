package com.sparta.eatsapp.auth.service;

import com.sparta.eatsapp.address.entity.Address;
import com.sparta.eatsapp.address.repository.AddressRepository;
import com.sparta.eatsapp.auth.dto.request.SigninRequest;
import com.sparta.eatsapp.auth.dto.request.SignupRequest;
import com.sparta.eatsapp.auth.dto.response.SignupResponse;
import com.sparta.eatsapp.config.JwtUtil;
import com.sparta.eatsapp.config.PasswordEncoder;
import com.sparta.eatsapp.password.entity.Password;
import com.sparta.eatsapp.password.repository.PasswordRepository;
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
  private final PasswordEncoder passwordEncoder;
  private final PasswordRepository passwordRepository;
  private final AddressRepository addressRepository;
  private final JwtUtil jwtUtil;

  @Transactional
  public SignupResponse signup(SignupRequest signupRequest) {

    String encryptedPassword = passwordEncoder.encode(signupRequest.getPwd());
    Password password = new Password(encryptedPassword);
    Address address = new Address(signupRequest.getAddress());

    User user = new User(
        signupRequest.getEmail(),
        signupRequest.getName(),
        UserRole.of(signupRequest.getRole()),
        signupRequest.getNickname()
    );
    user.addAddresses(address);
    user.setPassword(password);
    passwordRepository.save(password);
    addressRepository.save(address);
    User savedUser = userRepository.save(user);

    return new SignupResponse(savedUser);
  }

  public String signin(SigninRequest signinRequest) {
    User user = userRepository.findByEmail(signinRequest.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

    if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword().getPassword())) {
      throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
    };

    return jwtUtil.createToken(user.getId(), user.getEmail(), user.getRole());
  }
}
