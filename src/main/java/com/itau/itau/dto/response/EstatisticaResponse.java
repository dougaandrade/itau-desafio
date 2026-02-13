package com.itau.itau.dto.response;

import java.util.List;

import com.itau.itau.model.TransacaoModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstatisticaResponse {
  private Long count;
  private Double avg;
  private Double max;
  private Double min;
  private Double sum;
  private List<TransacaoModel> transacoes;
}
