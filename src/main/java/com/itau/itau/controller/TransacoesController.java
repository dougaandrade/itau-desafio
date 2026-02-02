package com.itau.itau.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.repository.TransacaoRepository;
import com.itau.itau.request.TransacaoRequest;
import com.itau.itau.service.TransacaoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transacao")
@AllArgsConstructor
public class TransacoesController {

  @Autowired
  private final TransacaoService transacaoService;

  @Autowired
  private final TransacaoRepository transacaoRepository;

  @PostMapping
  public ResponseEntity<TransacaoRequest> create(@RequestBody TransacaoRequest request) {
    try {
      log.info("Transacao processada com sucesso" + " ID: " + request.getId());
      return new ResponseEntity(transacaoService.newTrade(request), HttpStatus.CREATED);
    } catch (Exception exception) {
      log.error("Erro ao processar transacao: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // @DeleteMapping("/delete/{id}")
  // public ResponseEntity<String> delete(@PathVariable Long id) {

  // try {
  // transacaoRepository.deleteDataID(id);
  // log.info("Registro de transacao deletado com sucesso ID: {}", id);
  // return new ResponseEntity("Registro de transacao deletado com sucesso ID: " +
  // id, HttpStatus.OK);
  // } catch (Exception exception) {
  // log.error("Erro ao deletar transacoes: {}", exception.getMessage());
  // return new ResponseEntity("Erro: " + exception.getMessage(),
  // HttpStatus.BAD_REQUEST);
  // }

  // }

  @GetMapping()
  public ResponseEntity<?> findAll() {
    try {
      log.info("Transacoes recuperadas com sucesso");
      return new ResponseEntity(transacaoRepository.findAll(), HttpStatus.OK);
    } catch (Exception exception) {
      log.error("Erro ao recuperar transacoes: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("/buscar/{id}")
  public ResponseEntity<String> findById(@PathVariable Long id) {
    try {
      log.info("Transacao recuperada com sucesso ID: {}", id);
      return new ResponseEntity(transacaoRepository.findById(id), HttpStatus.OK);
    } catch (Exception exception) {
      log.error("Erro ao recuperar transacao: {}", exception.getMessage());
      return new ResponseEntity("Erro: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
