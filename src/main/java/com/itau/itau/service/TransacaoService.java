package com.itau.itau.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.exception.RateLimitExceededException;
import com.itau.itau.exception.TransacaoNotFoundException;
import com.itau.itau.exception.TransacaoValidationException;
import com.itau.itau.exception.UserNotFoundException;
import com.itau.itau.mapper.TransacaoMapper;
import com.itau.itau.model.TransacaoModel;
import com.itau.itau.model.UserModel;
import com.itau.itau.properties.TransacaoProperties;
import com.itau.itau.repository.TransacaoRepository;
import com.itau.itau.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransacaoService {

  private final TransacaoProperties transacaoProperties;
  private final TransacaoRepository transacaoRepository;
  private final TransacaoMapper transacaoMapper;
  private final UserRepository userRepository;

  @Transactional
  public TransacaoRequest newTrade(TransacaoRequest transacaoRequest) {
    validarTransacao(transacaoRequest);
    validarRateLimit();

    UserModel usuario = getAuthenticatedUser();

    TransacaoModel transacao = transacaoMapper.mapToModel(transacaoRequest);
    transacao.setUsuario(usuario);

    TransacaoModel transacaoSalva = transacaoRepository.save(transacao);
    return transacaoMapper.mapToRequest(transacaoSalva);
  }

  @Transactional(readOnly = true)
  public List<TransacaoModel> findAll() {
    UserModel usuario = getAuthenticatedUser();
    return transacaoRepository.findByUsuario(usuario);
  }

  @Transactional(readOnly = true)
  public TransacaoModel findById(Long id) {
    return transacaoRepository.findById(id)
        .orElseThrow(() -> new TransacaoNotFoundException(id));
  }

  private void validarTransacao(TransacaoRequest transacaoRequest) {
    if (transacaoRequest.getValor() == null) {
      throw new TransacaoValidationException("Valor da transação é obrigatório.");
    }
    if (transacaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0) {
      throw new TransacaoValidationException("Valor da transação deve ser maior que zero.");
    }
    if (transacaoProperties.valorMinimoPorTransacao() != null &&
        transacaoRequest.getValor().compareTo(transacaoProperties.valorMinimoPorTransacao()) < 0) {
      throw new TransacaoValidationException(
          String.format("Valor da transação (%.2f) é menor que o permitido (%.2f).",
              transacaoRequest.getValor(), transacaoProperties.valorMinimoPorTransacao()));
    }
    if (transacaoProperties.valorMaximoPorTransacao() != null &&
        transacaoRequest.getValor().compareTo(transacaoProperties.valorMaximoPorTransacao()) > 0) {
      throw new TransacaoValidationException(
          String.format("Valor da transação (%.2f) é maior que o permitido (%.2f).",
              transacaoRequest.getValor(), transacaoProperties.valorMaximoPorTransacao()));
    }
    transacaoRequest.setDataHora(LocalDateTime.now());
  }

  private void validarRateLimit() {
    UserModel usuario = getAuthenticatedUser();
    
    Long quantidadeTransacoesUltimoMinuto = transacaoRepository.findByUsuario(usuario).stream()
        .filter(transacao -> transacao.getDataHora().isAfter(LocalDateTime.now().minusMinutes(1)))
        .count();
    if (quantidadeTransacoesUltimoMinuto >= transacaoProperties.limitePorMinuto()) {
      throw new RateLimitExceededException("Limite de transações por minuto excedido.");
    }
  }

  private UserModel getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

}
