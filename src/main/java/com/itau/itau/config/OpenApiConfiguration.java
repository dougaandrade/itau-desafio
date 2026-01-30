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
    info.addExtension("x-logo",
        "https://www.itau.com.br/media/dam/m/59ca6000bfbf59d6/original/ITAU_LOGO_HEX_48X48.png");
    return new OpenAPI().info(info);
  }
}
