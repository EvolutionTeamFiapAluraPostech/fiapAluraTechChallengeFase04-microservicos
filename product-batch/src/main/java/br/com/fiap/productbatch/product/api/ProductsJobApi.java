package br.com.fiap.productbatch.product.api;

import java.util.Date;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/batch")
public class ProductsJobApi {

  private final JobLauncher jobLauncher;
  private final DataSource dataSource;
  private final Job jobProduct;

  public ProductsJobApi(JobLauncher jobLauncher,
      @Qualifier("productsDataSource") DataSource dataSource,
      @Qualifier("productJob") Job jobProduct) {
    this.jobLauncher = jobLauncher;
    this.dataSource = dataSource;
    this.jobProduct = jobProduct;
  }

  @PostMapping
  public ResponseEntity<String> runLogisticsJob() {
    return runJob(jobProduct);
  }

  private ResponseEntity<String> runJob(Job job) {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
          .addDate("date", new Date())
          .addString("jobName", job.getName())
          .toJobParameters();

      JobExecution jobExecution = jobLauncher.run(job, jobParameters);

      return ResponseEntity.ok("Batch Job " + job.getName()
          + " started with JobExecutionId: " + jobExecution.getId());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to start Batch Job: " + e.getMessage());
    }
  }
}
