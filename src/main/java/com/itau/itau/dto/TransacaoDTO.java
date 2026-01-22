package com.itau.itau.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransacaoDTO {

  private BigDecimal valor;
  private OffsetDateTime dataHora;
}
