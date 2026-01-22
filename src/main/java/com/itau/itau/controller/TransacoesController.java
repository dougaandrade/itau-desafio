package com.itau.itau.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.repository.TransacaoRepository;
import com.itau.itau.request.TransacaoRequest;
import com.itau.itau.service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacoesController {

  private final TransacaoService transacaoService;
  private final TransacaoRepository transacaoRepository;

  public TransacoesController(TransacaoService transacaoService, TransacaoRepository transacaoRepository) {
    this.transacaoService = transacaoService;
    this.transacaoRepository = transacaoRepository;
  }

  @PostMapping
  public ResponseEntity payment(@RequestBody TransacaoRequest transacaoRequest) {

    try {
      transacaoService.validarTransacao(transacaoRequest);
      transacaoRepository.saveData(transacaoRequest);
      return new ResponseEntity(HttpStatus.CREATED);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception exception) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

  }

  @DeleteMapping
  public ResponseEntity delete() {

    try {
      transacaoRepository.deleteData();
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

  }
}
