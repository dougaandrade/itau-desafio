package com.itau.itau.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.dto.response.TransacaoResponse;
import com.itau.itau.service.TransacaoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transacao")
@AllArgsConstructor
@Validated
public class TransacoesController {

  private final TransacaoService transacaoService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransacaoResponse> create(@Valid @RequestBody TransacaoRequest request) {
    TransacaoResponse response = transacaoService.newTrade(request);
    log.info("Transacao criada com sucesso: {}", response);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);

  }

  @GetMapping
  public ResponseEntity<List<TransacaoResponse>> allTransacoes() {
    List<TransacaoResponse> transacoes = transacaoService.findAll();
    log.info("Transacoes recuperadas com sucesso");
    return ResponseEntity.ok(transacoes);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<List<TransacaoResponse>> buscarPorUserId(@PathVariable Long id) {
    List<TransacaoResponse> transacoes = transacaoService.findTradebyUserId(id);
    log.info("Transacoes recuperadas com sucesso para o usuário ID: {}", id);
    return ResponseEntity.ok(transacoes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransacaoResponse> buscarPorId(@PathVariable Long id) {
    TransacaoResponse transacao = transacaoService.findById(id);
    log.info("Transacao recuperada com sucesso ID: {}", id);
    return ResponseEntity.ok(transacao);
  }
}
