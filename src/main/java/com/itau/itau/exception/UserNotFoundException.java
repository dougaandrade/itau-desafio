package com.itau.itau.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("Usuário autenticado não encontrado.");
  }
}
