package com.itau.itau.mapper;

import org.springframework.stereotype.Component;

import com.itau.itau.dto.EstatisticaDTO;
import com.itau.itau.dto.response.EstatisticaResponse;

@Component
public class EstatisticaMapper {

  public EstatisticaDTO mapToDTO(EstatisticaResponse response) {
    return EstatisticaDTO.builder()
        .id(response.id())
        .count(response.count())
        .avg(response.avg())
        .max(response.max())
        .min(response.min())
        .sum(response.sum())
        .transacoes(response.transacoes() != null && !response.transacoes().isEmpty()
            ? response.transacoes().get(0)
            : null)
        .build();
  }

}
