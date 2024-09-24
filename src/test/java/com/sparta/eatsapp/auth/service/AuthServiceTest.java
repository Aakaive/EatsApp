package com.sparta.eatsapp.auth.service;

import static com.sparta.eatsapp.TestData.signinRequest;
import static com.sparta.eatsapp.TestData.signupRequest;
import static com.sparta.eatsapp.TestData.testPassword;
import static com.sparta.eatsapp.TestData.testUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.sparta.eatsapp.address.repository.AddressRepository;
import com.sparta.eatsapp.auth.dto.response.SignupResponse;
import com.sparta.eatsapp.common.exception.NotFoundException;
import com.sparta.eatsapp.config.JwtUtil;
import com.sparta.eatsapp.config.PasswordEncoder;
import com.sparta.eatsapp.password.repository.PasswordRepository;
import com.sparta.eatsapp.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  public void setUp() {
    Long userid = 1L;
    ReflectionTestUtils.setField(testUser, "id", userid);
    ReflectionTestUtils.setField(testUser, "isDeleted", false);
    ReflectionTestUtils.setField(testUser, "password", testPassword);
  }

  @Test
  public void 회원가입_정상_동작() {
    //given

    given(userRepository.save(any())).willReturn(testUser);

    //when
    SignupResponse signupResponse = authService.signup(signupRequest);
    String email = signupResponse.getEmail();

    //then
    assertNotNull(signupResponse);
    assertEquals("dnwls111@naver.com", signupResponse.getEmail());
  }

  @Test
  public void 중복_메일_가입_시_IAE_에러() {
    //given
    given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(testUser));
    //when & then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> authService.signup(signupRequest)
    );
    assertEquals("이미 등록된 이메일 입니다.", exception.getMessage());
  }

  @Test
  public void 로그인_정상_동작() {
    //given
    String token = "Bearer Token";

    given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
    given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(testUser));
    given(jwtUtil.createToken(anyLong(), anyString(), any())).willReturn(token);

    //when & then
    assertThat(authService.signin(signinRequest)).startsWith("Bearer ");
  }

  @Test
  public void 등록되지_않은_이메일_로그인_IAE_에러() {
    //given
    given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
    //when & then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> authService.signin(signinRequest)
    );
    assertEquals("등록되지 않은 이메일입니다.", exception.getMessage());
  }

  @Test
  public void 삭제된_유저_로그인_시_NFE_에러() {
    //given
    ReflectionTestUtils.setField(testUser, "isDeleted", true);
    given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(testUser));
    //when & then
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> authService.signin(signinRequest)
    );
    assertEquals("삭제된 유저입니다.", exception.getMessage());
  }

  @Test
  public void 비밀번호가_틀렸을_경우_IAE_에러() {
    //given
    given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(testUser));
    given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);
    //when
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> authService.signin(signinRequest)
    );
    //then
    assertEquals("비밀번호가 틀렸습니다.", exception.getMessage());
  }
}
