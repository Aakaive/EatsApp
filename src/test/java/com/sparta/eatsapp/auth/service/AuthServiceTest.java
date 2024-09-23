package com.sparta.eatsapp.auth.service;

import static com.sparta.eatsapp.TestData.signupRequest;
import static com.sparta.eatsapp.TestData.testPassword;
import static com.sparta.eatsapp.TestData.testUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.sparta.eatsapp.address.repository.AddressRepository;
import com.sparta.eatsapp.auth.dto.request.SigninRequest;
import com.sparta.eatsapp.auth.dto.response.SignupResponse;
import com.sparta.eatsapp.config.JwtUtil;
import com.sparta.eatsapp.config.PasswordEncoder;
import com.sparta.eatsapp.password.repository.PasswordRepository;
import com.sparta.eatsapp.user.repository.UserRepository;
import java.util.Optional;
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

    Long userid = 1L;
    ReflectionTestUtils.setField(testUser, "id", userid);
    given(userRepository.save(any())).willReturn(testUser);

    //when
    SignupResponse signupResponse = authService.signup(signupRequest);
    String email = signupResponse.getEmail();

    //then
    assertNotNull(signupResponse);
    assertEquals("dnwls111@naver.com", signupResponse.getEmail());
  }

  @Test
  public void 로그인_정상_동작() {
    //given
    SigninRequest signinRequest = SigninRequest.builder()
        .email("dnwls111@naver.com")
        .password("dnwls111")
        .build();
    Long userid = 1L;
    ReflectionTestUtils.setField(testUser, "id", userid);
    ReflectionTestUtils.setField(testUser, "isDeleted", false);
    ReflectionTestUtils.setField(testUser, "password", testPassword);

    String token = "Bearer Token";

    given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
    given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(testUser));
    given(jwtUtil.createToken(anyLong(),anyString(),any())).willReturn(token);

    //when & then

    assertThat(authService.signin(signinRequest)).startsWith("Bearer ");
  }

}
