package com.itau.itau.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {
  private Long id;

  @NotNull(message = "O valor da transacao nao pode ser nulo")
  @Positive(message = "O valor da transacao deve ser maior que zero")
  private BigDecimal valor;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime dataHora;
}
