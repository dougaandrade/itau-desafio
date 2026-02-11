package com.itau.itau.exception;

/**
 * Exception thrown when an authenticated user is not found in the database.
 * The username parameter is used for internal logging only and is not included
 * in the error message to avoid exposing sensitive information.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("Usuário autenticado não encontrado.");
  }
}
