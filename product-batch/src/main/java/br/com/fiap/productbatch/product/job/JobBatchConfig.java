package br.com.fiap.productbatch.product.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobBatchConfig {

  @Bean
  public Job jobProduct(JobRepository jobRepository, @Qualifier("productStep") Step step) {
    return new JobBuilder("productJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
  }
}