package com.itau.itau.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.itau.itau.dto.request.TransacaoRequest;
import com.itau.itau.model.TransacaoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transações", description = "Endpoints para gerenciamento de transações financeiras")
public interface TransacaoControllerDoc {

  @Operation(summary = "Criar nova transação", description = "Cria uma nova transação financeira com validação de valor mínimo, máximo e rate limiting.", responses = {
      @ApiResponse(responseCode = "201", description = "Transação criada com sucesso", content = @Content(schema = @Schema(implementation = TransacaoRequest.class))),
      @ApiResponse(responseCode = "400", description = "Erro de validação - Valor inválido ou campos obrigatórios ausentes"),
      @ApiResponse(responseCode = "429", description = "Rate limit excedido - Limite de transações por minuto atingido"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  ResponseEntity<TransacaoRequest> create(
      @RequestBody(description = "Dados da transação. Deve conter valor maior que zero.", required = true) TransacaoRequest request);

  @Operation(summary = "Listar todas as transações", description = "Recupera todas as transações registradas no sistema.", responses = {
      @ApiResponse(responseCode = "200", description = "Transações recuperadas com sucesso", content = @Content(schema = @Schema(implementation = TransacaoModel.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  ResponseEntity<List<TransacaoModel>> allTransacoes();

  @Operation(summary = "Buscar transação por ID", description = "Recupera uma transação específica através do seu identificador único.", responses = {
      @ApiResponse(responseCode = "200", description = "Transação encontrada", content = @Content(schema = @Schema(implementation = TransacaoModel.class))),
      @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  ResponseEntity<TransacaoModel> buscarPorId(Long id);
}
