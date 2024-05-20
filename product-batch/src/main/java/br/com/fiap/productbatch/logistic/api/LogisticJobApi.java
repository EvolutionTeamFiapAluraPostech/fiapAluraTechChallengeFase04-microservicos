package br.com.fiap.productbatch.logistic.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "LogisticJobApi", description = "API para importação de CEPs")
public interface LogisticJobApi {

  @Operation(summary = "Importação de CEPs",
      description = "Endpoint para importar CEPs de uma fonte externa de dados (arquivo CSV).",
      tags = {"LogisticJobApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(schema = @Schema(hidden = true))}),
  })
  ResponseEntity<String> runLogisticsJob();
}
