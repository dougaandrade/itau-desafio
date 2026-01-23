package com.itau.itau.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "estatistica")
public record EstatisticaProperties(@NotNull @Positive Integer intervaloEmSegundos) {
}
