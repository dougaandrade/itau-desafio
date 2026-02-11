package com.itau.itau.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.itau.itau.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex, WebRequest request) {
    String message = String.format(
        "Content-Type '%s' não é suportado. Use 'application/json'",
        ex.getContentType());
    log.error("Media type não suportado: {}", ex.getContentType());
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
        "Unsupported Media Type",
        message,
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(TransacaoNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTransacaoNotFoundException(
      TransacaoNotFoundException ex, WebRequest request) {
    log.error("Transação não encontrada: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TransacaoValidationException.class)
  public ResponseEntity<ErrorResponse> handleTransacaoValidationException(
      TransacaoValidationException ex, WebRequest request) {
    log.error("Erro de validação: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RateLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleRateLimitExceededException(
      RateLimitExceededException ex, WebRequest request) {
    log.error("Rate limit excedido: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.TOO_MANY_REQUESTS.value(),
        "Too Many Requests",
        ex.getMessage(),
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex, WebRequest request) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .reduce((a, b) -> a + ", " + b)
        .orElse("Erro de validação");

    log.error("Erro de validação de campos: {}", message);
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        message,
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(
      Exception ex, WebRequest request) {
    log.error("Erro inesperado: {}", ex.getMessage(), ex);
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "Erro interno no servidor. Por favor, tente novamente mais tarde.",
        request.getDescription(false).replace("uri=", ""));
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
