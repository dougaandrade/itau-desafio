package com.itau.itau.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransacaoDTO {

  private BigDecimal valor;
  private LocalDateTime dataHora;
}
