package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.itau.itau.properties.TransacaoProperties;
import com.itau.itau.request.TransacaoRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransacaoService {

  private final TransacaoProperties transacaoProperties;

  public void validarTransacao(TransacaoRequest transacaoRequest) {

    if (transacaoRequest.getValor() == null) {
      throw new IllegalArgumentException("Valor da transação é obrigatório.");
    }
    if (transacaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
    }
    if (transacaoProperties.valorMinimoPorTransacao() != null &&
        transacaoRequest.getValor().compareTo(transacaoProperties.valorMinimoPorTransacao()) < 0) {
      throw new IllegalArgumentException(
          String.format("Valor da transação é menor que o permitido.",
              transacaoRequest.getValor(), transacaoProperties.valorMinimoPorTransacao()));
    }
    if (transacaoProperties.valorMaximoPorTransacao() != null &&
        transacaoRequest.getValor().compareTo(transacaoProperties.valorMaximoPorTransacao()) > 0) {
      throw new IllegalArgumentException(
          String.format("Valor da transação é maior que o permitido.",
              transacaoRequest.getValor(), transacaoProperties.valorMaximoPorTransacao()));
    }
    transacaoRequest.setDataHora(LocalDateTime.now());
    transacaoRequest.setId(System.currentTimeMillis());
  }

  public void validarRateLimit(int quantidadeTransacoesUltimoMinuto) {
    if (transacaoProperties.limitePorMinuto() == 0 ||
        quantidadeTransacoesUltimoMinuto < transacaoProperties.limitePorMinuto()) {
    } else {
      throw new IllegalArgumentException(
          String.format("Limite de transações por minuto excedido. Limite: %d, Atual: %d",
              transacaoProperties.limitePorMinuto(), quantidadeTransacoesUltimoMinuto));
    }
  }

  public boolean isTransacaoValida(LocalDateTime dataHoraTransacao,
      LocalDateTime horaAtual) {
    LocalDateTime limiteInferior = horaAtual.minusDays(1);
    return !dataHoraTransacao.isBefore(limiteInferior) &&
        !dataHoraTransacao.isAfter(horaAtual);
  }

}
