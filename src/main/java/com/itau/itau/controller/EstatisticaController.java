package com.itau.itau.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.properties.EstatisticaProperties;
import com.itau.itau.repository.TransacaoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/estatistica")
@AllArgsConstructor
public class EstatisticaController {

  private final EstatisticaProperties estatisticaProperties;

  private final TransacaoRepository transacaoRepository;

  // @GetMapping
  // public ResponseEntity<EstatisticaResponse> obterEstatisticas() {

  // log.info("Obtendo estatisticas");

  // LocalDateTime limiteInferior = LocalDateTime.now()
  // .minusSeconds(estatisticaProperties.intervaloEmSegundos());

  // if (LocalDateTime.now().isBefore(limiteInferior)) {
  // return ResponseEntity.ok(new EstatisticaResponse(0L, 0.0, 0.0, 0.0, 0.0));
  // }

  // return ResponseEntity.ok(transacaoRepository.obterEstatisticas());
  // }

}
