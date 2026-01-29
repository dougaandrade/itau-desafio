package com.itau.itau.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itau.itau.request.TransacaoRequest;
import com.itau.itau.response.EstatisticaResponse;

@Repository
public class TransacaoRepository {

  List<TransacaoRequest> transacaoList = new ArrayList<>();

  public void saveData(TransacaoRequest transacaoRequest) {
    transacaoList.add(transacaoRequest);

  }

  public void deleteData() {
    transacaoList.clear();
  }

  public List<TransacaoRequest> findAll() {
    return transacaoList;
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
