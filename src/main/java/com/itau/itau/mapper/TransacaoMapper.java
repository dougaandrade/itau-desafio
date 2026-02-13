package com.itau.itau.mapper;

import org.springframework.stereotype.Component;

import com.itau.itau.dto.TransacaoDTO;
import com.itau.itau.dto.UserDTO;
import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.dto.response.TransacaoResponse;
import com.itau.itau.model.TransacaoModel;
import com.itau.itau.model.UserModel;

@Component
public class TransacaoMapper {

  // Request → DTO (isolamento na entrada)
  public TransacaoDTO toDTO(TransacaoRequest request, UserModel usuario) {
    return TransacaoDTO.builder()
        .valor(request.getValor())
        .dataHora(request.getDataHora())
        .usuario(usuario)
        .build();
  }

  // Request → Model (direto para salvar no BD)
  public TransacaoModel toModel(TransacaoDTO dto) {
    return TransacaoModel.builder()
        .id(dto.getId())
        .valor(dto.getValor())
        .dataHora(dto.getDataHora())
        .usuario(dto.getUsuario())
        .build();
  }

  // Model → Response (isolamento na saída com UserDTO)
  public TransacaoResponse toResponse(TransacaoModel model) {
    UserDTO usuarioDTO = model.getUsuario() != null ? UserDTO.builder()
        .id(model.getUsuario().getId())
        .username(model.getUsuario().getUsername())
        .build()
        : null;

    return TransacaoResponse.builder()
        .id(model.getId())
        .valor(model.getValor())
        .dataHora(model.getDataHora())
        .usuario(usuarioDTO)
        .build();
  }

}
