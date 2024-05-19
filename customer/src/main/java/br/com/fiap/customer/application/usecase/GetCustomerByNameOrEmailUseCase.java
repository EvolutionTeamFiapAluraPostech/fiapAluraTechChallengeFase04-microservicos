package br.com.fiap.customer.application.usecase;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerByNameOrEmailUseCase {

  private final CustomerService customerService;

  public GetCustomerByNameOrEmailUseCase(CustomerService customerService) {
    this.customerService = customerService;
  }

  public Page<Customer> execute(String name, String email, Pageable pageable) {
    return customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(name, email, pageable);
  }
}
