package com.itau.itau.service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.itau.dto.response.EstatisticaResponse;
import com.itau.itau.model.TransacaoModel;
import com.itau.itau.repository.TransacaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstatisticaService {

  private final TransacaoRepository transacaoRepository;

  @Transactional
  public EstatisticaResponse obterEstatisticas() {
    List<TransacaoModel> transacoes = transacaoRepository.findAll();

    DoubleSummaryStatistics stats = transacoes.stream()
        .mapToDouble(transacao -> transacao.getValor().doubleValue())
        .summaryStatistics();

    return new EstatisticaResponse(
        null,
        stats.getCount(),
        stats.getCount() > 0 ? stats.getAverage() : 0.0,
        stats.getCount() > 0 ? stats.getMax() : 0.0,
        stats.getCount() > 0 ? stats.getMin() : 0.0,
        stats.getSum(),
        transacoes);

  }

  public EstatisticaResponse obterStatsPorUser(Long userId) {
    List<TransacaoModel> transacoes = transacaoRepository.findByUsuarioId(userId);

    DoubleSummaryStatistics stats = transacoes.stream()
        .mapToDouble(transacao -> transacao.getValor().doubleValue())
        .summaryStatistics();

    if (userId == null || transacoes.isEmpty()) {
      throw new RuntimeException("Usuário não encontrado ou sem transações.");
    }

    return new EstatisticaResponse(
        userId,
        stats.getCount(),
        stats.getCount() > 0 ? stats.getAverage() : 0.0,
        stats.getCount() > 0 ? stats.getMax() : 0.0,
        stats.getCount() > 0 ? stats.getMin() : 0.0,
        stats.getSum(),
        transacoes);
  }
}
