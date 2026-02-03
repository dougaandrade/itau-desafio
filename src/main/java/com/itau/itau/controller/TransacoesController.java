package com.itau.itau.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.repository.TransacaoRepository;
import com.itau.itau.service.TransacaoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transacao")
@AllArgsConstructor
public class TransacoesController {

  private final TransacaoService transacaoService;
  private final TransacaoRepository transacaoRepository;

  @PostMapping
  public ResponseEntity<TransacaoRequest> create(@RequestBody TransacaoRequest request) {
    try {
      TransacaoRequest response = (TransacaoRequest) transacaoService.newTrade(request);
      log.info("Transacao criada com sucesso: {}", response);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception exception) {
      log.error("Erro ao criar transacao: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping()
  public ResponseEntity<TransacaoRequest> allTransacoes() {
    try {
      log.info("Transacoes recuperadas com sucesso");
      return new ResponseEntity(transacaoRepository.findAll(), HttpStatus.OK);
    } catch (Exception exception) {
      log.error("Erro ao recuperar transacoes: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/buscar/{id}")
  public ResponseEntity<String> buscarID(@PathVariable Long id) {
    try {
      log.info("Transacao recuperada com sucesso ID: {}", id);
      return new ResponseEntity(transacaoRepository.findById(id), HttpStatus.OK);
    } catch (Exception exception) {
      log.error("Erro ao recuperar transacao: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
