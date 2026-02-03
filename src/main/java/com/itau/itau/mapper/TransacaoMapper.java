package com.itau.itau.mapper;

import org.springframework.stereotype.Component;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.model.TransacaoModel;


@Component
public class TransacaoMapper {

  public TransacaoModel mapToModel(TransacaoRequest request) {
    return TransacaoModel.builder()
        .valor(request.getValor())
        .dataHora(request.getDataHora())
        .build();
  }

  public TransacaoRequest mapToRequest(TransacaoModel model) {
    return TransacaoRequest.builder()
        .id(model.getId())
        .valor(model.getValor())
        .dataHora(model.getDataHora())
        .build();
  }

}
