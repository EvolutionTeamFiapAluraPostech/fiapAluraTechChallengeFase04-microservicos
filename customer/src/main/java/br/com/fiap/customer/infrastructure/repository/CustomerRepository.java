package br.com.fiap.customer.infrastructure.repository;

import br.com.fiap.customer.domain.entity.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, CustomerQueryRepository {

  Optional<Customer> findByDocNumber(String docNumber);
}
