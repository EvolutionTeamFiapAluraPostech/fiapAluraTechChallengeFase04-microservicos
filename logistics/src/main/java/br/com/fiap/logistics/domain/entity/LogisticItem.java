package br.com.fiap.logistics.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "logistics_management", name = "logistics_items")
public class LogisticItem extends BaseEntity {

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "logistics_id")
  private Logistic logistic;
  private UUID orderItemId;
  private UUID productId;
  private String productSku;
  private String productDescription;
  private BigDecimal quantity;
  private BigDecimal price;
  private BigDecimal totalAmount;
}
