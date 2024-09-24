package com.sparta.eatsapp.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninRequest {

  @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 다릅니다.")
  private String email;
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])[A-Za-z\\d@$!%*?&]+$", message = "비밀번호는 대소문자 포함 영문,숫자,특수문자를 최소 1글자씩 포함합니다.")
  private String password;

}
