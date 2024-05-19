package br.com.fiap.productbatch.logistic.step;

import br.com.fiap.productbatch.logistic.domain.PostalCode;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PostalCodeStepBatchConfig {

  @Bean("postalCodeStep")
  public Step postalCodeStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      @Qualifier("postalCodeItemReader") ItemReader<PostalCode> postalCodeItemReader,
      @Qualifier("postalCodeItemWriter") ItemWriter<PostalCode> postalCodeItemWriter) {
    return new StepBuilder("postalCodeStep", jobRepository)
        .<PostalCode, PostalCode>chunk(20, transactionManager)
        .reader(postalCodeItemReader)
        .writer(postalCodeItemWriter)
        .build();
  }
}
