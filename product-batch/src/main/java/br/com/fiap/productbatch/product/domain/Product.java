package br.com.fiap.productbatch.product.domain;

import java.time.LocalDateTime;

public class Product {

  private LocalDateTime createdAt;
  private Boolean active = true;
  private String sku;
  private String description;
  private Double quantityStock;
  private Double price;
  private String unitMeasurement;

  public Product() {
  }

  public Product(LocalDateTime createdAt, Boolean active, String sku, String description,
      Double quantityStock, Double price, String unitMeasurement) {
    this.createdAt = createdAt;
    this.active = active;
    this.sku = sku;
    this.description = description;
    this.quantityStock = quantityStock;
    this.price = price;
    this.unitMeasurement = unitMeasurement;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getQuantityStock() {
    return quantityStock;
  }

  public void setQuantityStock(Double quantityStock) {
    this.quantityStock = quantityStock;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getUnitMeasurement() {
    return unitMeasurement;
  }

  public void setUnitMeasurement(String unitMeasurement) {
    this.unitMeasurement = unitMeasurement;
  }
}
