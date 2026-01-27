package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.itau.itau.request.TransacaoRequest;

@Service
public class TransacaoService {

  public void validarTransacao(TransacaoRequest transacaoRequest) {

    if (transacaoRequest.getValor().compareTo(BigDecimal.ONE) <= 0) {
      throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
    }
    if (transacaoRequest.getValor() == null) {
      throw new IllegalArgumentException("Valor da transação é obrigatório.");
    }

    transacaoRequest.setDataHora(LocalDateTime.now());

  }

  public boolean isTransacaoValida(LocalDateTime dataHoraTransacao,
      LocalDateTime horaAtual) {
    LocalDateTime limiteInferior = horaAtual.minusDays(1);
    return !dataHoraTransacao.isBefore(limiteInferior) &&
        !dataHoraTransacao.isAfter(horaAtual);
  }

}
