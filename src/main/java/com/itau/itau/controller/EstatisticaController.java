package com.itau.itau.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.dto.response.EstatisticaResponse;
import com.itau.itau.service.EstatisticaService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/estatistica")
@AllArgsConstructor
public class EstatisticaController {

  private final EstatisticaService estatisticaService;

  @GetMapping
  public ResponseEntity<EstatisticaResponse> obterEstatisticas() {
    EstatisticaResponse estatistica = estatisticaService.obterEstatisticas();
    log.info("Estatisticas recuperadas com sucesso.");
    return ResponseEntity.ok(estatistica);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<EstatisticaResponse> obterStatsPorUser(@PathVariable Long userId) {
    try {
      log.info("Requisição para obter estatísticas do usuário ID: {}", userId);
      EstatisticaResponse estatistica = estatisticaService.obterStatsPorUser(userId);
      return ResponseEntity.ok(estatistica);
    } catch (RuntimeException e) {
      log.error("Erro ao obter estatísticas para o usuário ID: {}: {}", userId, e.getMessage());
      return ResponseEntity.status(404).body(null);
    }

  }
}
