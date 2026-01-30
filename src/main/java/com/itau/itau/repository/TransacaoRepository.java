package com.itau.itau.repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itau.itau.request.TransacaoRequest;
import com.itau.itau.response.EstatisticaResponse;

@Repository
public class TransacaoRepository {

  private final HashSet<TransacaoRequest> transacaoList = new HashSet<>();

  public void saveData(TransacaoRequest transacaoRequest) {
    transacaoList.add(transacaoRequest);

  }

  public TransacaoRequest deleteDataID(Long id) {

    return transacaoList.stream()
        .filter(transacao -> transacao.getId().equals(id))
        .findFirst()
        .map(transacao -> {
          transacaoList.remove(transacao);
          return transacao;
        })
        .orElseThrow(() -> new IllegalArgumentException("Transacao com ID " + id + " nao encontrada."));
  }

  public List<TransacaoRequest> findAll() {
    return List.copyOf(transacaoList);
  }

  public Integer contarTransacoesUltimoMinuto() {
    return (int) transacaoList.stream()
        .filter(transacao -> transacao.getDataHora().isAfter(LocalDateTime.now().minusMinutes(1)))
        .count();
  }

  public EstatisticaResponse obterEstatisticas() {

    if (transacaoList.isEmpty()) {
      return new EstatisticaResponse(0L, 0.0, 0.0, 0.0, 0.0);
    }

    final var summary = transacaoList.stream()
        .filter(transacao -> transacao.getDataHora().isAfter(LocalDateTime.now().minusMinutes(1)))
        .mapToDouble(transacao -> transacao.getValor().doubleValue())
        .summaryStatistics();

    return new EstatisticaResponse(summary.getCount(), summary.getAverage(), summary.getMax(),
        summary.getMin(), summary.getSum());

  }

}
