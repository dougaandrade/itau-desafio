package com.itau.itau.model;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Table(name = "estatisticas")
@AllArgsConstructor
@Data
public class EstatisticaModel {

  private long count;
  private Double avg;
  private Double max;
  private Double min;
  private Double sum;

}
