package br.com.fiap.productbatch.logistic.step.item;

import br.com.fiap.productbatch.logistic.domain.PostalCode;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PostalCodeReaderItemStepConfig {

  @Bean("postalCodeItemReader")
  public ItemReader<PostalCode> postalCodeItemReader() {
    var postalCodeBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<PostalCode>();
    postalCodeBeanWrapperFieldSetMapper.setTargetType(PostalCode.class);
    return new FlatFileItemReaderBuilder<PostalCode>()
        .name("postalCodeItemReader")
        .resource(new ClassPathResource("postcode_ranges.csv"))
        .delimited()
        .names("state", "locality", "postalCodeRange")
        .addComment("--")
        .fieldSetMapper(postalCodeBeanWrapperFieldSetMapper)
        .build();
  }
}
