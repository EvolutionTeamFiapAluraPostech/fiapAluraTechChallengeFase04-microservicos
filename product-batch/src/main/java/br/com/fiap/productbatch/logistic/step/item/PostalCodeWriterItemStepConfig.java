package br.com.fiap.productbatch.logistic.step.item;

import br.com.fiap.productbatch.logistic.domain.PostalCode;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostalCodeWriterItemStepConfig {

  @Bean("postalCodeItemWriter")
  public ItemWriter<PostalCode> postalCodeItemWriter(
      @Qualifier("logisticsDataSource") DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<PostalCode>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .dataSource(dataSource)
        .sql("""
            insert into logistics_management.postal_codes (state, locality, postal_code_range)
            values ( :state, :locality, :postalCodeRange)
            """)
        .build();
  }
}
