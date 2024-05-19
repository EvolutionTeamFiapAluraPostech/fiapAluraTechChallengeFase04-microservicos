package br.com.fiap.customer.presentation.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;

@Tag(name = "CustomerFilter", description = "DTO de entrada filtro de pesquisa de clientes")
public record CustomerFilter(
    @Schema(example = "Thomas Anderson", description = "Nome do cliente")
    String name,
    @Schema(example = "thomas.anderson@itcompany.com", description = "email do cliente")
    @Email
    String email) {
}
