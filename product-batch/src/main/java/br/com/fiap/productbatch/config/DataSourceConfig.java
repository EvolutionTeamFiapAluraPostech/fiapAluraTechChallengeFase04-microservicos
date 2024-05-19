package br.com.fiap.productbatch.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class DataSourceConfig {

  @Primary
  @Bean("batchDataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource batchCodeDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean("productsDataSource")
  @ConfigurationProperties(prefix = "product.datasource")
  public DataSource productDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean("logisticsDataSource")
  @ConfigurationProperties(prefix = "logistics.datasource")
  public DataSource postalCodeDataSource() {
    return DataSourceBuilder.create().build();
  }
}
