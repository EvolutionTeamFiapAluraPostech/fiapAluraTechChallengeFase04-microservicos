package br.com.fiap.logistics.presentation.api.dto;

import br.com.fiap.logistics.domain.entity.Logistic;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "LogisticOrderDto", description = "DTO de saída de pedido.")
public record LogisticOrderDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único da logística do pedido.")
    String id,
    @Schema(example = "ENTREGUE", description = "Status do pedido.")
    String status,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do pedido.")
    String orderId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único da empresa.")
    String companyId,
    @Schema(example = "Thomas Anderson", description = "Nome da empresa.", minLength = 3, maxLength = 500)
    String companyName,
    @Schema(example = "thomas.anderson@itcompany.com", description = "Endereço de e-mail da empresa.", minLength = 3, maxLength = 500)
    String companyEmail,
    @Schema(example = "11955975094", description = "Número do documento da empresa.", minLength = 11, maxLength = 14)
    String companyDocNumber,
    @Schema(example = "CPF ou CNPJ", description = "Tipo do número do documento da empresa.")
    String companyDocNumberType,
    @Schema(example = "Av. Lins de Vasconcelos", description = "Rua do endereço da empresa.", minLength = 3, maxLength = 255)
    String companyStreet,
    @Schema(example = "1222", description = "Número do endereço da empresa.", minLength = 3, maxLength = 255)
    String companyNumber,
    @Schema(example = "Cambuci", description = "Bairro do endereço da empresa.", minLength = 3, maxLength = 100)
    String companyNeighborhood,
    @Schema(example = "São Paulo", description = "Cidade do endereço da empresa.", minLength = 3, maxLength = 100)
    String companyCity,
    @Schema(example = "SP", description = "Sigla do Estado do endereço da empresa.", minLength = 2, maxLength = 2)
    String companyState,
    @Schema(example = "Brasil", description = "País do endereço da empresa.", minLength = 3, maxLength = 100)
    String companyCountry,
    @Schema(example = "01538001", description = "Código postal do endereço da empresa.", minLength = 8, maxLength = 8)
    String companyPostalCode,
    @Schema(example = "-23.56391", description = "Latitude.", minLength = -90, maxLength = 90)
    BigDecimal companyLatitude,
    @Schema(example = "-46.65239", description = "Longitude.", minLength = -90, maxLength = 90)
    BigDecimal companyLongitude,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do cliente.")
    String customerId,
    @Schema(example = "Thomas Anderson", description = "Nome do cliente.", minLength = 3, maxLength = 500)
    String customerName,
    @Schema(example = "thomas.anderson@itcompany.com", description = "Endereço de e-mail do cliente.", minLength = 3, maxLength = 500)
    String customerEmail,
    @Schema(example = "11955975094", description = "Número do documento do cliente.", minLength = 11, maxLength = 14)
    String customerDocNumber,
    @Schema(example = "CPF ou CNPJ", description = "Tipo do número do documento do cliente.")
    String customerDocNumberType,
    @Schema(example = "Av. Lins de Vasconcelos", description = "Rua do endereço do cliente.", minLength = 3, maxLength = 255)
    String customerStreet,
    @Schema(example = "1222", description = "Número do endereço do cliente.", minLength = 3, maxLength = 255)
    String customerNumber,
    @Schema(example = "Cambuci", description = "Bairro do endereço do cliente.", minLength = 3, maxLength = 100)
    String customerNeighborhood,
    @Schema(example = "São Paulo", description = "Cidade do endereço do cliente.", minLength = 3, maxLength = 100)
    String customerCity,
    @Schema(example = "SP", description = "Sigla do Estado do endereço do cliente.", minLength = 2, maxLength = 2)
    String customerState,
    @Schema(example = "Brasil", description = "País do endereço do cliente.", minLength = 3, maxLength = 100)
    String customerCountry,
    @Schema(example = "01538001", description = "Código postal do endereço do cliente.", minLength = 8, maxLength = 8)
    String customerPostalCode,
    @Schema(example = "-23.56391", description = "Latitude.", minLength = -90, maxLength = 90)
    BigDecimal customerLatitude,
    @Schema(example = "-46.65239", description = "Longitude.", minLength = -90, maxLength = 90)
    BigDecimal customerLongitude,
    @Schema(example = "{[]}", description = "Lista de produtos do pedido.")
    List<LogisticOrderItemDto> logisticsItems
) {

  public static LogisticOrderDto from(Logistic logistics) {
    var orderItemsDto = new ArrayList<LogisticOrderItemDto>();

    logistics.getLogisticsItems().forEach(item -> {
      var logisticsOrderItemDto = new LogisticOrderItemDto(
          item.getId() != null ? item.getId().toString() : null,
          item.getOrderItemId() != null ? item.getOrderItemId().toString() : null,
          item.getProductId() != null ? item.getProductId().toString() : null,
          item.getProductSku(), item.getProductDescription(), item.getQuantity(), item.getPrice());
      orderItemsDto.add(logisticsOrderItemDto);
    });

    return new LogisticOrderDto(logistics.getId() != null ? logistics.getId().toString() : null,
        logistics.getStatus().name(),
        logistics.getOrderId() != null ? logistics.getOrderId().toString() : null,
        logistics.getCompanyId() != null ? logistics.getCompanyId().toString() : null,
        logistics.getCompanyName(),
        logistics.getCompanyEmail(),
        logistics.getCompanyDocNumber(),
        logistics.getCompanyDocNumberType(),
        logistics.getCompanyStreet(),
        logistics.getCompanyNumber(),
        logistics.getCompanyNeighborhood(),
        logistics.getCompanyCity(),
        logistics.getCompanyState(),
        logistics.getCompanyCountry(),
        logistics.getCompanyPostalCode(),
        logistics.getCompanyLatitude(),
        logistics.getCompanyLongitude(),
        logistics.getCustomerId() != null ? logistics.getCustomerId().toString() : null,
        logistics.getCustomerName(),
        logistics.getCustomerEmail(),
        logistics.getCustomerDocNumber(),
        logistics.getCustomerDocNumberType(),
        logistics.getCustomerStreet(),
        logistics.getCustomerNumber(),
        logistics.getCustomerNeighborhood(),
        logistics.getCustomerCity(),
        logistics.getCustomerState(),
        logistics.getCustomerCountry(),
        logistics.getCustomerPostalCode(),
        logistics.getCustomerLatitude(),
        logistics.getCustomerLongitude(),
        orderItemsDto);
  }
}
