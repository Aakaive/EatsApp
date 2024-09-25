package com.sparta.eatsapp.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException, IOException {
    // Customize the response when access is denied

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    SecurityErrorResponse errorResponse = new SecurityErrorResponse(HttpStatus.FORBIDDEN,"api에 접근할 권한이 없습니다.");
    JSONObject jsonObject = new JSONObject(errorResponse);
    response.getWriter().write(jsonObject.toString());
  }
}
