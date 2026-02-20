package com.itau.itau.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transacoes")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransacaoModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private BigDecimal valor;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime dataHora;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private UserModel usuario;

  @JsonProperty("usuario")
  public String getUsuarioUsername() {
    return usuario != null ? usuario.getUsername() : null;
  }

}
