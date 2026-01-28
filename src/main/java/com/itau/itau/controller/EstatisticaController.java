package com.itau.itau.controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.dto.EstatisticaDTO;
import com.itau.itau.properties.EstatisticaProperties;
import com.itau.itau.repository.TransacaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

  @Autowired
  private EstatisticaProperties estatisticaProperties;

  @Autowired
  private TransacaoRepository transacaoRepository;

  @GetMapping
  public ResponseEntity obterEstatisticas() {

    log.info("Obtendo estatisticas");

    LocalDateTime limiteInferior = LocalDateTime.now(OffsetDateTime.now().getOffset())
        .minusSeconds(estatisticaProperties.intervaloEmSegundos());

    if (LocalDateTime.now(OffsetDateTime.now().getOffset()).isBefore(limiteInferior)) {
      return ResponseEntity.ok(new EstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0));
    }

    return ResponseEntity.ok(transacaoRepository.obterEstatisticas());
  }

}
