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

@Tag(name = "LogisticsApi", description = "API de logística de entrega de pedidos")
public interface LogisticsOrderApi {

  @Operation(summary = "Cadastro de pedidos",
      description = "Endpoint para cadastrar os pedidos pagos e que serão entregues.",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = LogisticOrderDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação do id da empresa, do id do cliente, id do produto, quantidade e preço.",
          content = {@Content(schema = @Schema(hidden = true))})})
  LogisticOrderDto postOrderLogistics(
      @Parameter(description =
          "DTO de entrada com atributos para se cadastrar o pedido a ser entregue. "
              + "Campos obrigatórios id da empresa, id do cliente, id do produto, quantidade e preço unitário.")
      LogisticsOrderInputDto logisticsOrderInputDto);

  @Operation(summary = "Recupera um pedido de entrega",
      description = "Endpoint para recuperar um pedido de entrega pelo ID cadastrado",
      tags = {"LogisticsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LogisticOrderDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para pedido não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  LogisticOrderDto getLogisticsOrderById(
      @Parameter(description = "UUID válido de um pedido") String id);

  @Operation(summary = "Pedido pronto para entrega",
      description = "Endpoint para autorizar a entrega do pedido",
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
      @Parameter(description = "UUID válido de um pedido") String id);

  @Operation(summary = "Confirma a entrega do pedido",
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
