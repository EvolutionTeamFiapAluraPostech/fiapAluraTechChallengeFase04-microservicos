package br.com.fiap.productbatch.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "ProductsJobApi", description = "API para importação de produtos")
public interface ProductsJobApi {

  @Operation(summary = "Importação de produtos",
      description = "Endpoint para importar produtos de uma fonte externa de dados (arquivo CSV).",
      tags = {"ProductsJobApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(schema = @Schema(hidden = true))}),
  })
  ResponseEntity<String> runLogisticsJob();
}
