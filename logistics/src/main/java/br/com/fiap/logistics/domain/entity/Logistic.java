package br.com.fiap.logistics.domain.entity;


import br.com.fiap.logistics.domain.enums.LogisticEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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
@Table(schema = "logistics_management", name = "logistics")
@SQLRestriction("deleted = false")
public class Logistic extends BaseEntity {

  @JsonIgnore
  @Builder.Default
  private Boolean active = true;
  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private LogisticEnum status;
  private UUID companyId;
  private String companyName;
  private String companyEmail;
  private String companyDocNumber;
  private String companyDocNumberType;
  private String companyStreet;
  private String companyNumber;
  private String companyNeighborhood;
  private String companyCity;
  private String companyState;
  private String companyCountry;
  private String companyPostalCode;
  private BigDecimal companyLatitude;
  private BigDecimal companyLongitude;
  private UUID orderId;
  private UUID customerId;
  private String customerName;
  private String customerEmail;
  private String customerDocNumber;
  private String customerDocNumberType;
  private String customerStreet;
  private String customerNumber;
  private String customerNeighborhood;
  private String customerCity;
  private String customerState;
  private String customerCountry;
  private String customerPostalCode;
  private BigDecimal customerLatitude;
  private BigDecimal customerLongitude;
  @OneToMany(targetEntity = LogisticItem.class, mappedBy = "logistic", cascade = CascadeType.ALL)
  private List<LogisticItem> logisticsItems;

  @Override
  public String toString() {
    return "Logistic{" +
        "id=" + id +
        '}';
  }
}
