package br.com.fiap.productbatch.product.step.item;

import br.com.fiap.productbatch.product.domain.Product;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductItemWriterStepConfig {

  @Bean("productWriterItemStep")
  public ItemWriter<Product> productItemWriter(
      @Qualifier("productsDataSource") DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Product>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .dataSource(dataSource)
        .sql("""
            insert into product_management.products (sku, description, quantity_stock, price, unit_measurement, version, active, created_at)
            values ( :sku, :description, :quantityStock, :price, :unitMeasurement, 0, true, now())
            """)
        .build();
  }
}
