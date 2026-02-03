package com.itau.itau.dto;

import com.itau.itau.model.TransacaoModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EstatisticaDTO {

  private final Long id;

  private final long count;
  private final Double avg;
  private final Double max;
  private final Double min;
  private final Double sum;
  private final TransacaoModel transacoes;

}
