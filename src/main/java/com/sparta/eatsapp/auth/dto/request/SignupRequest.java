package com.sparta.eatsapp.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequest {

  @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 다릅니다.")
  private String email;
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])[A-Za-z\\d@$!%*?&]+$", message = "비밀번호는 대소문자 포함 영문,숫자,특수문자를 최소 1글자씩 포함합니다.")
  @Size(min = 8, max = 64, message = "비밀번호는 최소 8자 최대 64자 입니다.")
  private String password;
  private String name;
  private String address;
  private String location;
  private String role;
  private String nickname;
}
