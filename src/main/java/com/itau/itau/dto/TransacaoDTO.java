package com.itau.itau.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.itau.itau.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoDTO {

  private Long id;
  private BigDecimal valor;
  private LocalDateTime dataHora;
  private UserModel usuario;
}
