package com.itau.itau.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI openApi() {
    Info info = new Info()
        .title("API de Transações + Desafios Itau Java")
        .description("API para simulação de transações bancárias")
        .version("1.0.0");
    info.addExtension("x-logo", "https://itau.com.br/logo.png");
    return new OpenAPI().info(info);
  }
}
