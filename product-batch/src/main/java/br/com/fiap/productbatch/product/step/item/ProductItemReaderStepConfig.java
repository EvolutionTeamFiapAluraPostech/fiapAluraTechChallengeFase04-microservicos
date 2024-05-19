package br.com.fiap.productbatch.product.step.item;

import br.com.fiap.productbatch.product.domain.Product;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ProductItemReaderStepConfig {

  @Bean("productItemReaderStep")
  public ItemReader<Product> productItemReader() {
    var productBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Product>();
    productBeanWrapperFieldSetMapper.setTargetType(Product.class);
    return new FlatFileItemReaderBuilder<Product>()
        .name("productItemReader")
        .resource(new ClassPathResource("products.csv"))
        .delimited()
        .names("sku", "description", "quantityStock", "price", "unitMeasurement")
        .addComment("--")
        .fieldSetMapper(productBeanWrapperFieldSetMapper)
        .build();
  }
}
