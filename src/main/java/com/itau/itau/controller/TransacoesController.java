package com.itau.itau.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.repository.TransacaoRepository;
import com.itau.itau.request.TransacaoRequest;
import com.itau.itau.service.TransacaoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacoesController {

  @Autowired
  private TransacaoService transacaoService;
  @Autowired
  private TransacaoRepository transacaoRepository;

  @PostMapping
  public ResponseEntity payment(@RequestBody TransacaoRequest transacaoRequest) {
    try {
      transacaoService.validarTransacao(transacaoRequest);
      transacaoRepository.saveData(transacaoRequest);
      transacaoService.isTransacaoValida(transacaoRequest.getDataHora(), transacaoRequest.getDataHora());
      log.info("Transacao processada com sucesso");
      return new ResponseEntity("Transacao criada com sucesso", HttpStatus.CREATED);
    } catch (IllegalArgumentException exception) {
      log.error("Erro ao processar transacao: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception exception) {
      log.error("Erro ao processar transacao: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @DeleteMapping
  public ResponseEntity<String> delete() {

    try {
      transacaoRepository.deleteData();
      log.info("Todas as transacoes foram deletadas com sucesso");
      return new ResponseEntity<>("Todas as transacoes foram deletadas com sucesso", HttpStatus.OK);
    } catch (Exception exception) {
      log.error("Erro ao deletar transacoes: {}", exception.getMessage());
      return new ResponseEntity<>("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping()
  public ResponseEntity<TransacaoRequest> findAll() {
    try {
      log.info("Transacoes recuperadas com sucesso");
      return new ResponseEntity(transacaoRepository.findAll(), HttpStatus.OK);
    } catch (Exception exception) {
      log.error("Erro ao recuperar transacoes: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }
}
