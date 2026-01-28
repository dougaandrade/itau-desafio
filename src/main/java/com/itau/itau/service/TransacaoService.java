package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.itau.itau.request.TransacaoRequest;

@Service
public class TransacaoService {

  public void validarTransacao(TransacaoRequest transacaoRequest) {

    if (transacaoRequest.getValor() == null) {
      throw new IllegalArgumentException("Valor da transação é obrigatório.");
    }
    if (transacaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
    }

    transacaoRequest.setDataHora(LocalDateTime.now());
    transacaoRequest.setId(System.currentTimeMillis());
    

  }

  public boolean isTransacaoValida(LocalDateTime dataHoraTransacao,
      LocalDateTime horaAtual) {
    LocalDateTime limiteInferior = horaAtual.minusDays(1);
    return !dataHoraTransacao.isBefore(limiteInferior) &&
        !dataHoraTransacao.isAfter(horaAtual);
  }

}
