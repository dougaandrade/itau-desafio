package com.itau.itau.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.model.TransacaoModel;
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

  @PostMapping
  public ResponseEntity<TransacaoRequest> create(@Valid @RequestBody TransacaoRequest request) {
    TransacaoRequest response = transacaoService.newTrade(request);
    log.info("Transacao criada com sucesso: {}", response);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);

  }

  @GetMapping
  public ResponseEntity<List<TransacaoModel>> allTransacoes() {
    List<TransacaoModel> transacoes = transacaoService.findAll();
    log.info("Transacoes recuperadas com sucesso");
    return ResponseEntity.ok(transacoes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransacaoModel> buscarPorId(@PathVariable Long id) {
    TransacaoModel transacao = transacaoService.findById(id);
    log.info("Transacao recuperada com sucesso ID: {}", id);
    return ResponseEntity.ok(transacao);
  }
}
