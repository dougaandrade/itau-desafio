package com.itau.itau.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    try {
      EstatisticaResponse estatistica = estatisticaService.obterEstatisticas();
      log.info("Estatisticas recuperadas com sucesso.");
      return ResponseEntity.ok(estatistica);
    } catch (Exception exception) {
      log.error("Erro ao recuperar estatisticas: {}", exception.getMessage());
      return ResponseEntity.badRequest().build();
    }

  }

}
