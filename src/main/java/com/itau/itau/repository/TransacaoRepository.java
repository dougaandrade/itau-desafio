package com.itau.itau.repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itau.itau.dto.EstatisticaDTO;
import com.itau.itau.request.TransacaoRequest;

@Repository
public class TransacaoRepository {

  List<TransacaoRequest> transacaoList = new ArrayList<>();

  public void saveData(TransacaoRequest transacaoRequest) {
    transacaoList.add(transacaoRequest);

  }

  public void deleteData() {
    transacaoList.clear();
  }

  public void clearData() {

  }

  public List<TransacaoRequest> findAll() {
    return transacaoList;
  }

  public EstatisticaDTO obterEstatisticas(OffsetDateTime horaAtual) {

    if (transacaoList.isEmpty()) {
      return new EstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0);
    }

    final var summary = transacaoList.stream()
        .filter(transacao -> transacao.getDataHora().isAfter(horaAtual) ||
            transacao.getDataHora().isEqual(horaAtual))
        .mapToDouble(transacao -> transacao.getValor().doubleValue())
        .summaryStatistics();

    return new EstatisticaDTO(summary.getCount(), summary.getAverage(), summary.getMax(),
        summary.getMin(), summary.getSum());

  }

}
