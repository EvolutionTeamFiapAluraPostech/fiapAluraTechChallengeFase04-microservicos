package br.com.fiap.logistics.presentation.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Tag(name = "OrderItemInputDto", description = "DTO de entrada de item de pedido.")
public record LogisticsOrderItemInputDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do item do pedido.")
    @NotBlank
    String orderItemId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do produto.")
    @NotBlank
    String productId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do produto na loja.")
    @NotBlank
    @Size(min = 1, max = 20, message = "O tamanho do sku deve estar entre 1 e 20")
    String productSku,
    @Schema(example = "Teclado ergonômico ABNT 2", description = "Descrição do produto.")
    @NotBlank
    @Size(min = 3, max = 500, message = "O tamanho da descrição deve estar entre 3 e 500")
    String productDescription,
    @Schema(example = "1", description = "Quantidade do produto.")
    @NotNull
    @Positive
    BigDecimal quantity,
    @Schema(example = "1.25", description = "Preço do produto.")
    @NotNull
    @Positive
    BigDecimal price
) {

}
