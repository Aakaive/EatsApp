package com.sparta.eatsapp.config;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

  // @Auth 어노테이션이 있는지 확인
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(Auth.class) != null;
  }

  // AuthUser 객체를 생성하여 반환
  @Override
  public Object resolveArgument(
      @Nullable MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory
  ) {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    // JwtFilter 에서 set 한 userId, email 값을 가져옴
    Long userId = (Long) request.getAttribute("userId");
    String email = (String) request.getAttribute("email");

    return new AuthUser(userId, email);
  }
}
