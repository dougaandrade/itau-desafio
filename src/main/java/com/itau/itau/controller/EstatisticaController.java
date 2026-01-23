package com.itau.itau.controller;

import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.properties.EstatisticaProperties;
import com.itau.itau.repository.TransacaoRepository;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

  private final EstatisticaProperties estatisticaProperties;
  private final TransacaoRepository transacaoRepository;

  public EstatisticaController(EstatisticaProperties estatisticaProperties, TransacaoRepository transacaoRepository) {
    this.estatisticaProperties = estatisticaProperties;
    this.transacaoRepository = transacaoRepository;
  }

  @GetMapping
  public ResponseEntity obterEstatisticas() {
    final var horaAtual = OffsetDateTime.now()
        .minusSeconds(estatisticaProperties.intervaloEmSegundos());

    return ResponseEntity.ok(transacaoRepository.obterEstatisticas(horaAtual));
  }

}
