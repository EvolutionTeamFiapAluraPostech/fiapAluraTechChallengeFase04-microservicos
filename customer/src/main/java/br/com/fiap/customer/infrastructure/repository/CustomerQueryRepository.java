package br.com.fiap.customer.infrastructure.repository;

import br.com.fiap.customer.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface CustomerQueryRepository {

  @Query(value = """
          SELECT c
          FROM Customer c
          WHERE (:name IS NULL OR UPPER(TRIM(c.name)) LIKE CONCAT('%', UPPER(TRIM(:name)), '%'))
            AND (:email IS NULL OR UPPER(TRIM(c.email)) LIKE CONCAT('%', UPPER(TRIM(:email)), '%'))
      """)
  Page<Customer> queryCustomersByNameLikeIgnoreCaseOrEmail(String name, String email, Pageable pageable);
}
