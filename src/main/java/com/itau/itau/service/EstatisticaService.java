package com.itau.itau.service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.itau.dto.response.EstatisticaResponse;
import com.itau.itau.model.EstatisticaModel;
import com.itau.itau.model.TransacaoModel;
import com.itau.itau.repository.EstatisticaRepository;
import com.itau.itau.repository.TransacaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstatisticaService {

  private final TransacaoRepository transacaoRepository;
  private final EstatisticaRepository estatisticaRepository;

  @Transactional
  public EstatisticaResponse obterEstatisticas() {
    List<TransacaoModel> transacoes = transacaoRepository.findAll();

    DoubleSummaryStatistics stats = transacoes.stream()
        .mapToDouble(transacao -> transacao.getValor().doubleValue())
        .summaryStatistics();

    EstatisticaModel estatistica = EstatisticaModel.builder()
        .count(stats.getCount())
        .avg(stats.getCount() > 0 ? stats.getAverage() : 0.0)
        .max(stats.getCount() > 0 ? stats.getMax() : 0.0)
        .min(stats.getCount() > 0 ? stats.getMin() : 0.0)
        .sum(stats.getSum())
        .transacoes(transacoes)
        .build();

    estatisticaRepository.save(estatistica);

    return new EstatisticaResponse(
        estatistica.getCount(),
        estatistica.getAvg(),
        estatistica.getMax(),
        estatistica.getMin(),
        estatistica.getSum(),
        transacoes);
  }
}