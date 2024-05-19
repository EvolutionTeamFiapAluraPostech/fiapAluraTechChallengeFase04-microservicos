package br.com.fiap.customer.application.usecase;

import br.com.fiap.customer.application.validator.UuidValidator;
import br.com.fiap.customer.domain.service.CustomerService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCustomerUseCase {

  private final CustomerService customerService;
  private final UuidValidator uuidValidator;

  public DeleteCustomerUseCase(CustomerService customerService, UuidValidator uuidValidator) {
    this.customerService = customerService;
    this.uuidValidator = uuidValidator;
  }

  @Transactional
  public void execute(String customerId) {
    uuidValidator.validate(customerId);
    var customer = customerService.findByIdRequired(UUID.fromString(customerId));
    customer.setDeleted(true);
    customerService.save(customer);
  }
}
