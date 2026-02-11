package com.itau.itau.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.itau.itau.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(TransacaoNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTransacaoNotFoundException(
      TransacaoNotFoundException ex, WebRequest request) {
    log.error("Transação não encontrada: {}", ex.getMessage());
    return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "Not Found", request);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(
      UserNotFoundException ex, WebRequest request) {
    log.error("Usuário não encontrado: {}", ex.getMessage());
    return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, "Not Found", request);
  }

  @ExceptionHandler(TransacaoValidationException.class)
  public ResponseEntity<ErrorResponse> handleTransacaoValidationException(
      TransacaoValidationException ex, WebRequest request) {
    log.error("Erro de validação: {}", ex.getMessage());
    return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request", request);
  }

  @ExceptionHandler(RateLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleRateLimitExceededException(
      RateLimitExceededException ex, WebRequest request) {
    log.error("Rate limit excedido: {}", ex.getMessage());
    return createErrorResponse(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests", request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex, WebRequest request) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .reduce((a, b) -> a + ", " + b)
        .orElse("Erro de validação");

    log.error("Erro de validação de campos: {}", message);
    return createErrorResponse(message, HttpStatus.BAD_REQUEST, "Bad Request", request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(
      Exception ex, WebRequest request) {
    log.error("Erro inesperado: {}", ex.getMessage(), ex);
    return createErrorResponse(
        "Erro interno no servidor. Por favor, tente novamente mais tarde.",
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal Server Error",
        request);
  }

  /**
   * Creates a standardized error response.
   *
   * @param message The error message to be returned to the client
   * @param status The HTTP status code
   * @param error The error type/category description
   * @param request The web request that generated the error
   * @return ResponseEntity containing the error response with appropriate HTTP status
   */
  private ResponseEntity<ErrorResponse> createErrorResponse(
      String message, HttpStatus status, String error, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
        LocalDateTime.now(),
        status.value(),
        error,
        message,
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(errorResponse, status);
  }
}
