package br.com.fiap.productbatch.product.step;

import br.com.fiap.productbatch.product.domain.Product;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class StepBatchConfig {

  @Bean("productStep")
  public Step productStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      @Qualifier("productItemReaderStep") ItemReader<Product> productItemReader,
      @Qualifier("productWriterItemStep") ItemWriter<Product> productItemWriter,
      @Qualifier("productItemProcessorStep") ItemProcessor<Product, Product> productItemProcessor) {
    return new StepBuilder("productStep", jobRepository)
        .<Product, Product>chunk(20, transactionManager)
        .reader(productItemReader)
        .processor(productItemProcessor)
        .writer(productItemWriter)
        .build();
  }
}
