package com.itau.itau.properties;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "transacoes")
public record TransacaoProperties(
    int limitePorMinuto,
    BigDecimal valorMaximoPorTransacao,
    BigDecimal valorMinimoPorTransacao) {
}