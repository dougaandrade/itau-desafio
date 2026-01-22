package com.itau.itau.dto;

import lombok.Getter;

@Getter
public class EstatisticaDTO {

  private final long count;
  private final Double avg;
  private final Double max;
  private final Double min;
  private final Double sum;

  public EstatisticaDTO(long count, Double avg, Double max, Double min, Double sum) {
    this.count = count;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.sum = sum;
  }

}
