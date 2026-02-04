package com.itau.itau.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "estatisticas")
@AllArgsConstructor
@Data
@Entity
@Builder
@NoArgsConstructor
public class EstatisticaModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private long count;
  private Double avg;
  private Double max;
  private Double min;
  private Double sum;

  @JsonIgnoreProperties("estatisticas")
  @ManyToMany(fetch = FetchType.EAGER)
  private List<TransacaoModel> transacoes;
}
