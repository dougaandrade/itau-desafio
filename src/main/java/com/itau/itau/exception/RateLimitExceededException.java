package com.itau.itau.exception;

public class RateLimitExceededException extends RuntimeException {
  public RateLimitExceededException(String message) {
    super(message);
  }
}
