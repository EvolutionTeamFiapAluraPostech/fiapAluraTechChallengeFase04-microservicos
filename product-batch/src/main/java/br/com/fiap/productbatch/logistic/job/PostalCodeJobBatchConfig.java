package br.com.fiap.productbatch.logistic.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostalCodeJobBatchConfig {

  @Bean("postalCodeJob")
  public Job postalCodeJob(JobRepository jobRepository, @Qualifier("postalCodeStep") Step step) {
    return new JobBuilder("postalCodeJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
  }
}
