package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.itau.itau.request.TransacaoRequest;

@Service
public class TransacaoService {

  public void validarTransacao(TransacaoRequest transacaoRequest) {

    if (transacaoRequest.getValor().compareTo(BigDecimal.ONE) <= 0) {
      throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
    }

    if (transacaoRequest.getDataHora().isAfter(OffsetDateTime.now())) {
      throw new IllegalArgumentException("Data da transação nao deve ser futura.");
    }

    if (transacaoRequest.getDataHora() == null) {
      throw new IllegalArgumentException("Data da transação é obrigatória.");
    }

    if (transacaoRequest.getValor() == null) {
      throw new IllegalArgumentException("Valor da transação é obrigatório.");
    }

  }

}
