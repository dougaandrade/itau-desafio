package com.itau.itau.docs;

import org.springframework.http.ResponseEntity;

import com.itau.itau.dto.response.EstatisticaResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estatísticas", description = "Endpoints para consulta de estatísticas das transações")
public interface EstatisticaControllerDoc {

  @Operation(summary = "Obter estatísticas das transações", description = "Calcula e retorna estatísticas agregadas de todas as transações registradas, incluindo contagem, média, valor máximo, valor mínimo e soma total. Também retorna a lista de transações utilizadas no cálculo.", responses = {
      @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso", content = @Content(schema = @Schema(implementation = EstatisticaResponse.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  ResponseEntity<EstatisticaResponse> obterEstatisticas();
}