package com.itau.itau.docs;

import org.springframework.http.ResponseEntity;

import com.itau.itau.dto.request.TransacaoRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transaçoes", description = "Endpoints de criacação e deleção de transaçoes")
public interface TransacaoControllerDoc {

  @Operation(summary = "Cria Transacao", description = "Recebe uma transacao valida e adiciona em uma lista")
  @ApiResponse(responseCode = "201", description = "Transaçao criada com sucesso")

  @ApiResponse(responseCode = "422", description = "Erro de validaçao capturado")
  @ApiResponse(responseCode = "400", description = "Erro inesperado no servidor")
  ResponseEntity<Void> payment(@RequestBody TransacaoRequest transacaoRequest);

  @Operation(summary = "Remove transaçao por id", description = "Recebe um id e remove da lista de transaçoes")
  @ApiResponse(responseCode = "201", description = "Transaçao removida com sucesso")
  ResponseEntity<Void> delete();

}