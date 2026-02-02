package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.itau.itau.mapper.TransacaoMapper;
import com.itau.itau.model.TransacaoModel;
import com.itau.itau.properties.TransacaoProperties;
import com.itau.itau.repository.TransacaoRepository;
import com.itau.itau.request.TransacaoRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransacaoService {

  private final TransacaoProperties transacaoProperties;
  private final TransacaoRepository transacaoRepository;
  private final TransacaoMapper transacaoMapper;

  public TransacaoRequest newTrade(TransacaoRequest transacaoRequest) {
    validarTransacao(transacaoRequest);
    TransacaoModel transacao = transacaoMapper.mapToModel(transacaoRequest);
    TransacaoModel transacaoSalva = transacaoRepository.save(transacao);
    return transacaoMapper.mapToRequest(transacaoSalva);
  }

  // public TransacaoRequest deleteDataID(Long id) {

  // return transacaoList.stream()
  // .filter(transacao -> transacao.getId().equals(id))
  // .findFirst()
  // .map(transacao -> {
  // transacaoList.remove(transacao);
  // return transacao;
  // })
  // .orElseThrow(() -> new IllegalArgumentException("Transacao com ID " + id + "
  // nao encontrada."));
  // }

  // public List<TransacaoRequest> findAll() {
  // return List.copyOf(transacaoList);
  // }

  // public Integer contarTransacoesUltimoMinuto() {
  // return (int) transacaoList.stream()
  // .filter(transacao ->
  // transacao.getDataHora().isAfter(LocalDateTime.now().minusMinutes(1)))
  // .count();
  // }

  // public EstatisticaResponse obterEstatisticas() {

  // if (transacaoList.isEmpty()) {
  // return new EstatisticaResponse(0L, 0.0, 0.0, 0.0, 0.0);
  // }

  // final var summary = transacaoList.stream()
  // .filter(transacao ->
  // transacao.getDataHora().isAfter(LocalDateTime.now().minusMinutes(1)))
  // .mapToDouble(transacao -> transacao.getValor().doubleValue())
  // .summaryStatistics();

  // return new EstatisticaResponse(summary.getCount(), summary.getAverage(),
  // summary.getMax(),
  // summary.getMin(), summary.getSum());

  // }

  // public TransacaoRequest findById(Long id) {
  // return transacaoList.stream()
  // .filter(transacao -> transacao.getId().equals(id))
  // .findFirst()
  // .orElseThrow(() -> new IllegalArgumentException("Transacao com ID " + id + "
  // nao encontrada."));

  // }

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

  private void validarRateLimit(Integer quantidadeTransacoesUltimoMinuto) {
    if (transacaoProperties.limitePorMinuto() != null &&
        transacaoProperties.limitePorMinuto() > 0 &&
        quantidadeTransacoesUltimoMinuto >= transacaoProperties.limitePorMinuto()) {
      throw new IllegalArgumentException(
          String.format("Limite de transações por minuto excedido. Limite: %d, Atual: %d",
              transacaoProperties.limitePorMinuto(), quantidadeTransacoesUltimoMinuto));
    }
  }

  private boolean isTransacaoValida(LocalDateTime dataHoraTransacao,
      LocalDateTime horaAtual) {
    LocalDateTime limiteInferior = horaAtual.minusDays(1);
    return !dataHoraTransacao.isBefore(limiteInferior) &&
        !dataHoraTransacao.isAfter(horaAtual);
  }

}
