package br.com.fiap.logistics.presentation.api.dto;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.entity.LogisticItem;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "OrderInputDto", description = "DTO de entrada de pedido.")
public record LogisticsOrderInputDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único da empresa.")
    @NotBlank
    String companyId,
    @Schema(example = "Thomas Anderson", description = "Nome da empresa.", minLength = 3, maxLength = 500)
    @NotBlank
    @Size(min = 3, max = 500, message = "Size must be between 3 e 500 characters.")
    String companyName,
    @Schema(example = "thomas.anderson@itcompany.com", description = "Endereço de e-mail da empresa.", minLength = 3, maxLength = 500)
    @NotBlank
    @Size(min = 3, max = 500, message = "Size must be between 3 e 500 characters.")
    @Email
    String companyEmail,
    @Schema(example = "11955975094", description = "Número do documento da empresa.", minLength = 11, maxLength = 14)
    @NotBlank
    @Size(min = 11, max = 14, message = "Size must be between 11 e 14 characters.")
    String companyDocNumber,
    @Schema(example = "CPF ou CNPJ", description = "Tipo do número do documento da empresa.")
    String companyDocNumberType,
    @Schema(example = "Av. Lins de Vasconcelos", description = "Rua do endereço da empresa.", minLength = 3, maxLength = 255)
    @NotBlank
    @Size(min = 3, max = 255, message = "Size must be between 3 e 255 characters.")
    String companyStreet,
    @Schema(example = "1222", description = "Número do endereço da empresa.", minLength = 3, maxLength = 255)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String companyNumber,
    @Schema(example = "Cambuci", description = "Bairro do endereço da empresa.", minLength = 3, maxLength = 100)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String companyNeighborhood,
    @Schema(example = "São Paulo", description = "Cidade do endereço da empresa.", minLength = 3, maxLength = 100)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String companyCity,
    @Schema(example = "SP", description = "Sigla do Estado do endereço da empresa.", minLength = 2, maxLength = 2)
    @NotBlank
    @Size(min = 2, max = 2, message = "Size must be 2 characters.")
    String companyState,
    @Schema(example = "Brasil", description = "País do endereço da empresa.", minLength = 3, maxLength = 100)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String companyCountry,
    @Schema(example = "01538001", description = "Código postal do endereço da empresa.", minLength = 8, maxLength = 8)
    @NotBlank
    @Size(min = 8, max = 8, message = "Size must be between 8 characters.")
    @Digits(integer = 8, fraction = 0, message = "Postal code must be a valid number with a maximum of 8 integral digits")
    String companyPostalCode,
    @Schema(example = "-23.56391", description = "Latitude.", minLength = -90, maxLength = 90)
    BigDecimal companyLatitude,
    @Schema(example = "-46.65239", description = "Longitude.", minLength = -90, maxLength = 90)
    BigDecimal companyLongitude,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do pedido.")
    @NotEmpty
    String orderId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do cliente.")
    @NotBlank
    String customerId,
    @Schema(example = "Thomas Anderson", description = "Nome do cliente.", minLength = 3, maxLength = 500)
    @NotBlank
    @Size(min = 3, max = 500, message = "Size must be between 3 e 500 characters.")
    String customerName,
    @Schema(example = "thomas.anderson@itcompany.com", description = "Endereço de e-mail do cliente.", minLength = 3, maxLength = 500)
    @NotBlank
    @Size(min = 3, max = 500, message = "Size must be between 3 e 500 characters.")
    @Email
    String customerEmail,
    @Schema(example = "11955975094", description = "Número do documento do cliente.", minLength = 11, maxLength = 14)
    @NotBlank
    @Size(min = 11, max = 14, message = "Size must be between 11 e 14 characters.")
    String customerDocNumber,
    @Schema(example = "CPF ou CNPJ", description = "Tipo do número do documento do cliente.")
    String customerDocNumberType,
    @Schema(example = "Av. Lins de Vasconcelos", description = "Rua do endereço do cliente.", minLength = 3, maxLength = 255)
    @NotBlank
    @Size(min = 3, max = 255, message = "Size must be between 3 e 255 characters.")
    String customerStreet,
    @Schema(example = "1222", description = "Número do endereço do cliente.", minLength = 3, maxLength = 255)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String customerNumber,
    @Schema(example = "Cambuci", description = "Bairro do endereço do cliente.", minLength = 3, maxLength = 100)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String customerNeighborhood,
    @Schema(example = "São Paulo", description = "Cidade do endereço do cliente.", minLength = 3, maxLength = 100)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String customerCity,
    @Schema(example = "SP", description = "Sigla do Estado do endereço do cliente.", minLength = 2, maxLength = 2)
    @NotBlank
    @Size(min = 2, max = 2, message = "Size must be 2 characters.")
    String customerState,
    @Schema(example = "Brasil", description = "País do endereço do cliente.", minLength = 3, maxLength = 100)
    @NotBlank
    @Size(min = 3, max = 100, message = "Size must be between 3 e 100 characters.")
    String customerCountry,
    @Schema(example = "01538001", description = "Código postal do endereço do cliente.", minLength = 8, maxLength = 8)
    @NotBlank
    @Size(min = 8, max = 8, message = "Size must be between 8 characters.")
    @Digits(integer = 8, fraction = 0, message = "Postal code must be a valid number with a maximum of 8 integral digits")
    String customerPostalCode,
    @Schema(example = "-23.56391", description = "Latitude.", minLength = -90, maxLength = 90)
    BigDecimal customerLatitude,
    @Schema(example = "-46.65239", description = "Longitude.", minLength = -90, maxLength = 90)
    BigDecimal customerLongitude,
    @Schema(example = "{[]}", description = "Lista de produtos do pedido.")
    @NotEmpty
    @Size(min = 1)
    List<@Valid LogisticsOrderItemInputDto> logisticsItems
) {

  public Logistic toLogistics() {
    var logisticsOrderItems = new ArrayList<LogisticItem>();

    var logistic = Logistic.builder()
        .companyId(UUID.fromString(this.companyId))
        .companyName(this.companyName)
        .companyEmail(this.companyEmail)
        .companyDocNumber(this.companyDocNumber)
        .companyDocNumberType(this.companyDocNumberType)
        .companyStreet(this.companyStreet)
        .companyNumber(this.companyNumber)
        .companyNeighborhood(this.companyNeighborhood)
        .companyCity(this.companyCity)
        .companyState(this.companyState)
        .companyCountry(this.companyCountry)
        .companyPostalCode(this.companyPostalCode)
        .companyLatitude(this.companyLatitude)
        .companyLongitude(this.companyLongitude)
        .orderId(UUID.fromString(this.orderId))
        .customerId(UUID.fromString(this.customerId))
        .customerName(this.customerName)
        .customerEmail(this.customerEmail)
        .customerDocNumber(this.customerDocNumber)
        .customerDocNumberType(this.customerDocNumberType)
        .customerStreet(this.customerStreet)
        .customerNumber(this.customerNumber)
        .customerNeighborhood(this.customerNeighborhood)
        .customerCity(this.customerCity)
        .customerState(this.customerState)
        .customerCountry(this.customerCountry)
        .customerPostalCode(this.customerPostalCode)
        .customerLatitude(this.customerLatitude)
        .customerLongitude(this.customerLongitude)
        .logisticsItems(logisticsOrderItems)
        .build();

    logisticsItems.forEach(item -> {
      var logisticsItem = LogisticItem.builder()
          .logistic(logistic)
          .orderItemId(UUID.fromString(item.orderItemId()))
          .productId(UUID.fromString(item.productId()))
          .productSku(item.productSku())
          .productDescription(item.productDescription())
          .quantity(item.quantity())
          .price(item.price())
          .build();
      logisticsOrderItems.add(logisticsItem);
    });

    return logistic;
  }
}
