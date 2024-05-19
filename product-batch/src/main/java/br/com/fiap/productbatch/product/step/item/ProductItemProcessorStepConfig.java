package br.com.fiap.productbatch.product.step.item;

import br.com.fiap.productbatch.product.domain.Product;
import java.util.HashSet;
import java.util.Set;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductItemProcessorStepConfig {

  private final Set<String> skus = new HashSet<>();

  @Bean("productItemProcessorStep")
  public ItemProcessor<Product, Product> productItemProcessor() throws Exception {
    return new CompositeItemProcessorBuilder<Product, Product>()
        .delegates(beanValidatingItemProcessor(), skuValidatingItemProcessor())
        .build();
  }

  private ValidatingItemProcessor<Product> skuValidatingItemProcessor() {
    var processor = new ValidatingItemProcessor<Product>();
    processor.setValidator(validator());
    processor.setFilter(true);
    return processor;
  }

  private Validator<Product> validator() {
    return product -> {
      if (skus.contains(product.getSku())) {
        throw new ValidationException(
            "Um produto com o sku %s já foi importado.".formatted(product.getSku()));
      }
      skus.add(product.getSku());
    };
  }

  private BeanValidatingItemProcessor<Product> beanValidatingItemProcessor() throws Exception {
    var processor = new BeanValidatingItemProcessor<Product>();
    processor.setFilter(true);
    processor.afterPropertiesSet();
    return processor;
  }
}
