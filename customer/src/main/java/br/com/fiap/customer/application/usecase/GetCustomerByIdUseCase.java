package br.com.fiap.customer.application.usecase;

import br.com.fiap.customer.application.validator.UuidValidator;
import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.service.CustomerService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerByIdUseCase {

  private final CustomerService customerService;
  private final UuidValidator uuidValidator;

  public GetCustomerByIdUseCase(CustomerService customerService, UuidValidator uuidValidator) {
    this.customerService = customerService;
    this.uuidValidator = uuidValidator;
  }

  public Customer execute(String id) {
    uuidValidator.validate(id);
    return customerService.findByIdRequired(UUID.fromString(id));
  }
}
