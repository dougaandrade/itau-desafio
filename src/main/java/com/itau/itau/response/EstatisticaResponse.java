package com.itau.itau.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EstatisticaResponse {
  private Long count;
  private Double avg;
  private Double max;
  private Double min;
  private Double sum;

}
