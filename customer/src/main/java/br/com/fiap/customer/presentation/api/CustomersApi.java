package br.com.fiap.customer.presentation.api;

import br.com.fiap.customer.presentation.api.dto.CustomerFilter;
import br.com.fiap.customer.presentation.api.dto.CustomerInputDto;
import br.com.fiap.customer.presentation.api.dto.CustomerOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "CustomersApi", description = "API de cadastro de clientes")
public interface CustomersApi {

  @Operation(summary = "Cadastro de clientes",
      description = "Endpoint para cadastrar novos clientes.",
      tags = {"CustomersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação de nome, e-mail, número do documento (CPF/CNPJ), rua, número, bairro, cidade, Estado, país e CEP.",
          content = {@Content(schema = @Schema(hidden = true))})})
  CustomerOutputDto postCustomer(
      @Parameter(description = "DTO com atributos para se cadastrar um novo cliente. Requer validação de nome, e-mail, número do documento (CPF/CNPJ), rua, número, bairro, cidade, Estado, país e CEP.")
      CustomerInputDto customerInputDto);

  @Operation(summary = "Recupera um cliente",
      description = "Endpoint para recuperar um cliente pelo ID cadastrado",
      tags = {"CustomersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para cliente não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  CustomerOutputDto getCustomerById(
      @Parameter(description = "UUID válido de um cliente") String id);

  @Operation(summary = "Atualiza um cliente",
      description = "Endpoint para atualizar dados de um cliente",
      tags = {"CustomersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para cliente não encontrado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "409", description = "conflict para CPF/CNPJ já cadastrado em outro cliente", content = {
          @Content(schema = @Schema(hidden = true))})})
  CustomerOutputDto putCustomer(@Parameter(description = "UUID válido de um cliente") String id,
      @Parameter(description = "DTO com atributos para se atualizar um cliente. Requer validação de nome, e-mail, número do documento (CPF/CNPJ), rua, número, bairro, cidade, Estado, país e CEP.")
      CustomerInputDto customerInputDto);

  @Operation(summary = "Exclui um cliente",
      description = "Endpoint para excluir um cliente. A exclusão é feita por soft delete",
      tags = {"CustomersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "successful operation", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para cliente não encontrado", content = {
          @Content(schema = @Schema(hidden = true))})})
  void deleteCustomer(@Parameter(description = "UUID válido do cliente") String id);

  @Operation(summary = "Lista de clientes paginada",
      description = "Endpoint para recuperar uma lista paginada de clientes, filtrada por nome OU email, ordenada por nome",
      tags = {"CustomersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para cliente não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  Page<CustomerOutputDto> getCustomersByNameOrEmail(
      @Parameter(description = "DTO com os atributos nome ou email para serem utilizados como filtro de pesquisa.") CustomerFilter customerFilter,
      @Parameter(description = "Interface com atributos para paginação") Pageable pageable);
}
