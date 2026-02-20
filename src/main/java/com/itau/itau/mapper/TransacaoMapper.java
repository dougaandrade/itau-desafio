package com.itau.itau.mapper;

import java.util.Optional;

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
  public TransacaoDTO toDTO(TransacaoRequest request, UserModel userModel) {
    return TransacaoDTO.builder()
        .valor(request.getValor())
        .dataHora(request.getDataHora())
        .usuario(userModel)
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
    UserDTO usuarioDTO = Optional.ofNullable(model.getUsuario())
        .map(u -> UserDTO.builder()
            .id(u.getId())
            .username(u.getUsername())
            .build())
        .orElse(null);

    return TransacaoResponse.builder()
        .id(model.getId())
        .valor(model.getValor())
        .dataHora(model.getDataHora())
        .usuario(usuarioDTO)
        .build();
  }
}
