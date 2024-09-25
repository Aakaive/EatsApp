package com.sparta.eatsapp.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SecurityErrorResponse {
  private String httpStatus;
  private String message;

  public SecurityErrorResponse(HttpStatus httpStatus, String message){
    this.httpStatus = httpStatus.toString();
    this.message = message;
  }

}
