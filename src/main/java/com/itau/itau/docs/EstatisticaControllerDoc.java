package com.itau.itau.docs;

import org.springframework.http.ResponseEntity;

import com.itau.itau.dto.EstatisticaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estatisticas", description = "Operacoes relacionadas a estatisticas de transacoes")
public interface EstatisticaControllerDoc {

  @Operation(summary = "Obtem Estatisticas", description = "Obtem as estatisticas de transações")
  @ApiResponse(responseCode = "200", description = "Estatisticas obtidas com sucesso")
  @ApiResponse(responseCode = "400", description = "Erro ao obter estatísticas")
  ResponseEntity<EstatisticaDTO> obterEstatisticas();

}