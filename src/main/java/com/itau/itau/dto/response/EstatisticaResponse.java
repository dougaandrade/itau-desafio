package com.itau.itau.dto.response;

import java.util.List;

import com.itau.itau.model.TransacaoModel;

import lombok.Builder;

@Builder
public record EstatisticaResponse(Long id, Long count, Double avg, Double max, Double min, Double sum,
    List<TransacaoModel> transacoes) {
}
