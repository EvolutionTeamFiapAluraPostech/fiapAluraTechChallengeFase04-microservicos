package br.com.fiap.logistics.presentation.api;

import br.com.fiap.logistics.presentation.api.dto.LogisticOrderDto;
import br.com.fiap.logistics.presentation.api.dto.LogisticsOrderInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "LogisticsApi", description = "API de logística de ordem de entrega de pedidos")
public interface LogisticsOrderApi {

  @Operation(summary = "Cadastro de ordem de entrega de pedidos",
      description = "Endpoint para cadastrar a ordem de entrega do pedidos pagos e que serão entregues.",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = LogisticOrderDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação do id da empresa, do id do cliente, id do produto, quantidade e preço.",
          content = {@Content(schema = @Schema(hidden = true))})})
  LogisticOrderDto postOrderLogistics(
      @Parameter(description =
          "DTO de entrada com atributos para se cadastrar a ordem de entrega de pedidos. "
              + "Campos obrigatórios id da empresa, id do cliente, id do produto, quantidade e preço unitário.")
      LogisticsOrderInputDto logisticsOrderInputDto);

  @Operation(summary = "Recupera uma ordem de entrega de pedidos",
      description = "Endpoint para recuperar uma ordem de entrega de pedido pelo seu ID",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LogisticOrderDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para ordem de entrega de pedido não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  LogisticOrderDto getLogisticsOrderById(
      @Parameter(description = "UUID válido de uma ordem de entrega de pedidos") String id);

  @Operation(summary = "Recupera um pedido de entrega",
      description = "Endpoint para recuperar uma ordem de entrega de pedidos pelo ID do pedido de venda cadastrado",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LogisticOrderDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para uma ordem de entrega de pedido não encontrada",
          content = {@Content(schema = @Schema(hidden = true))})})
  LogisticOrderDto getLogisticByOrderId(
      @Parameter(description = "UUID válido de um pedido de venda") String id);

  @Operation(summary = "Ordem de entrega de pedido pronta para ser enviada",
      description = "Endpoint para autorizar a ordem de entrega de pedido",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido ou entrega já realizada", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para pedido de entrega não encontrado", content = {
          @Content(schema = @Schema(hidden = true))}),
  })
  void patchLogisticsOrderIsReadyToDelivery(
      @Parameter(description = "UUID válido de uma ordem de entrega de pedido") String id);

  @Operation(summary = "Confirma a ordem de entrega de pedido",
      description = "Endpoint para confirmar a entrega do pedido",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido ou entrega já realizada", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para pedido de entrega não encontrado", content = {
          @Content(schema = @Schema(hidden = true))}),
  })
  void patchLogisticsOrderDeliveryConfirmation(
      @Parameter(description = "UUID válido de um pedido") String id);
}
