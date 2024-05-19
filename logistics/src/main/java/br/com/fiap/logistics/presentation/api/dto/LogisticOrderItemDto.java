package br.com.fiap.logistics.presentation.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Tag(name = "LogisticOrderItemDto", description = "DTO de saída do item do pedido.")
public record LogisticOrderItemDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do item da logística.")
    String id,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do item do pedido.")
    String orderItemId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do item do produto.")
    String productId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do produto na loja.")
    @NotBlank
    @Size(min = 1, max = 20, message = "O tamanho do sku deve estar entre 1 e 20")
    String productSku,
    @Schema(example = "Teclado ergonômico ABNT 2", description = "Descrição do produto.")
    @NotBlank
    @Size(min = 3, max = 500, message = "O tamanho da descrição deve estar entre 3 e 500")
    String productDescription,
    @Schema(example = "10.00", description = "Quantidade do produto.")
    BigDecimal quantity,
    @Schema(example = "315.00", description = "Preço unitário do produto.")
    BigDecimal price,
    @Schema(example = "3150.00", description = "Valor total do item do pedido.")
    BigDecimal totalAmout
) {

  public LogisticOrderItemDto(String id, String orderItemId, String productId, String productSku,
      String productDescription, BigDecimal quantity, BigDecimal price) {
    this(id, orderItemId, productId, productSku, productDescription, quantity, price,
        BigDecimal.ZERO);
  }
}
