package com.itau.itau.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.itau.itau.dto.UserDTO;

import lombok.Builder;

@Builder
public record TransacaoResponse(
    Long id,
    BigDecimal valor,
    LocalDateTime dataHora,
    UserDTO usuario) {

}
