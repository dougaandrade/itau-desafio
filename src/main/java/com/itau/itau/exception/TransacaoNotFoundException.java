package com.itau.itau.exception;

public class TransacaoNotFoundException extends RuntimeException {
  public TransacaoNotFoundException(Long id) {
    super("Transação com ID " + id + " não encontrada.");
  }
}
