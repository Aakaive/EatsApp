package com.sparta.eatsapp.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.sparta.eatsapp.address.repository.AddressRepository;
import com.sparta.eatsapp.auth.dto.request.SignupRequest;
import com.sparta.eatsapp.auth.dto.response.SignupResponse;
import com.sparta.eatsapp.config.JwtUtil;
import com.sparta.eatsapp.config.PasswordEncoder;
import com.sparta.eatsapp.password.repository.PasswordRepository;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import com.sparta.eatsapp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private PasswordRepository passwordRepository;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private AuthService authService;

  @Test
  public void 회원가입_정상_동작() {
    //given
    SignupRequest signupRequest = SignupRequest.builder()
        .email("dnwls111@naver.com")
        .password("dnwls111")
        .name("김우진")
        .address("서울특별시")
        .location("집")
        .role("USER")
        .nickname("testNick")
        .build();
    Long userid = 1L;
    User user = new User(
        signupRequest.getEmail(),
        signupRequest.getName(),
        UserRole.of(signupRequest.getRole()),
        signupRequest.getNickname()
    );
    ReflectionTestUtils.setField(user,"id",userid);
    given(userRepository.save(any())).willReturn(user);

    //when
    SignupResponse signupResponse = authService.signup(signupRequest);
    String email = signupResponse.getEmail();

    //then
    assertNotNull(signupResponse);
    assertEquals("dnwls111@naver.com",signupResponse.getEmail());
  }


}
