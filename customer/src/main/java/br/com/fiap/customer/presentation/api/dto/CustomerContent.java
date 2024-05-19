package br.com.fiap.customer.presentation.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Tag(name = "CustomerContent", description = "DTO de saída representação de um cliente")
@Getter
@Setter
public class CustomerContent {

  @Schema(description = "Lista de DTO de clientes")
  private List<CustomerOutputDto> content;
}
