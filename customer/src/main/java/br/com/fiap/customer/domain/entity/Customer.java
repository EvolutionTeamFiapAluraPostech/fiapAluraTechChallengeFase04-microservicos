package br.com.fiap.customer.domain.entity;

import br.com.fiap.customer.domain.enums.DocNumberType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "customer_management", name = "customers")
@SQLRestriction("deleted = false")
public class Customer extends BaseEntity {

  @Builder.Default
  private Boolean active = true;
  private String name;
  private String email;
  private String docNumber;
  @Enumerated(EnumType.STRING)
  private DocNumberType docNumberType;
  private String street;
  private String number;
  private String neighborhood;
  private String city;
  private String state;
  private String country;
  private String postalCode;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
