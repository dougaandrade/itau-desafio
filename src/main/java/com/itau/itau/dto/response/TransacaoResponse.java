package com.itau.itau.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itau.itau.dto.UserDTO;

import lombok.Builder;

@Builder
public record TransacaoResponse(
    Long id,
    BigDecimal valor,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dataHora,
    UserDTO usuario) {

}
