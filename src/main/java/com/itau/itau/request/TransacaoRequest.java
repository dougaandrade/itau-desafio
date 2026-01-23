package com.itau.itau.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {

  @NotNull(message = "O valor da transacao nao pode ser nulo")
  @Positive(message = "O valor da transacao deve ser maior que zero")
  private BigDecimal valor;
  private OffsetDateTime dataHora;
}
