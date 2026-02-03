package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.mapper.TransacaoMapper;
import com.itau.itau.model.TransacaoModel;
import com.itau.itau.properties.TransacaoProperties;
import com.itau.itau.repository.TransacaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransacaoService {

  private final TransacaoProperties transacaoProperties;
  private final TransacaoRepository transacaoRepository;
  private final TransacaoMapper transacaoMapper;

  public TransacaoRequest newTrade(TransacaoRequest transacaoRequest) {
    try {
      validarTransacao(transacaoRequest);
      validarRateLimit();
      TransacaoModel transacaoSalva = transacaoRepository.save(transacaoMapper.mapToModel(transacaoRequest));
      return transacaoMapper.mapToRequest(transacaoSalva);
    } catch (Exception exception) {
      throw new IllegalArgumentException("Erro ao processar transacao: " + exception.getMessage());
    }
  }

  private void validarTransacao(TransacaoRequest transacaoRequest) {

    if (transacaoRequest.getValor() == null) {
      throw new IllegalArgumentException("Valor da transação é obrigatório.");
    }
    if (transacaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
    }
    if (transacaoProperties.valorMinimoPorTransacao() != null &&
        transacaoRequest.getValor().compareTo(transacaoProperties.valorMinimoPorTransacao()) < 0) {
      throw new IllegalArgumentException(
          String.format("Valor da transação (%.2f) é menor que o permitido (%.2f).",
              transacaoRequest.getValor(), transacaoProperties.valorMinimoPorTransacao()));
    }
    if (transacaoProperties.valorMaximoPorTransacao() != null &&
        transacaoRequest.getValor().compareTo(transacaoProperties.valorMaximoPorTransacao()) > 0) {
      throw new IllegalArgumentException(
          String.format("Valor da transação (%.2f) é maior que o permitido (%.2f).",
              transacaoRequest.getValor(), transacaoProperties.valorMaximoPorTransacao()));
    }

    transacaoRequest.setDataHora(LocalDateTime.now());

  }

  private void validarRateLimit() {
    Long quantidadeTransacoesUltimoMinuto = transacaoRepository.findAll().stream()
        .filter(transacao -> transacao.getDataHora().isAfter(LocalDateTime.now().minusMinutes(2)))
        .count();
    if (quantidadeTransacoesUltimoMinuto >= transacaoProperties.limitePorMinuto()) {
      throw new IllegalArgumentException("Limite de transações por minuto excedido.");
    }
  }

}
