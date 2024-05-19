package br.com.fiap.customer.domain.service;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_ID_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.exception.NoResultException;
import br.com.fiap.customer.infrastructure.repository.CustomerRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  public Optional<Customer> findByDocNumber(String docNumber) {
    return customerRepository.findByDocNumber(docNumber);
  }

  public boolean isCustomerDocNumberAlreadyExists(String docNumber) {
    return this.findByDocNumber(docNumber).isPresent();
  }

  public Customer findByIdRequired(UUID uuid) {
    return customerRepository.findById(uuid).orElseThrow(
        () -> new NoResultException(
            new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
                CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(uuid.toString()))));
  }

  public Page<Customer> queryCustomersByNameLikeIgnoreCaseOrEmail(String name, String email,
      Pageable pageable) {
    return customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(name, email, pageable);
  }
}
