package br.com.fiap.customer.presentation.api;

import br.com.fiap.customer.application.usecase.CreateCustomerUseCase;
import br.com.fiap.customer.application.usecase.DeleteCustomerUseCase;
import br.com.fiap.customer.application.usecase.GetCustomerByIdUseCase;
import br.com.fiap.customer.application.usecase.GetCustomerByNameOrEmailUseCase;
import br.com.fiap.customer.application.usecase.UpdateCustomerUseCase;
import br.com.fiap.customer.presentation.api.dto.CustomerFilter;
import br.com.fiap.customer.presentation.api.dto.CustomerInputDto;
import br.com.fiap.customer.presentation.api.dto.CustomerOutputDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomersController implements CustomersApi {

  private final CreateCustomerUseCase createCustomerUseCase;
  private final GetCustomerByIdUseCase getCustomerByIdUseCase;
  private final UpdateCustomerUseCase updateCustomerUseCase;
  private final DeleteCustomerUseCase deleteCustomerUseCase;
  private final GetCustomerByNameOrEmailUseCase getCustomerByNameOrEmailUseCase;

  public CustomersController(CreateCustomerUseCase createCustomerUseCase,
      GetCustomerByIdUseCase getCustomerByIdUseCase, UpdateCustomerUseCase updateCustomerUseCase,
      DeleteCustomerUseCase deleteCustomerUseCase,
      GetCustomerByNameOrEmailUseCase getCustomerByNameOrEmailUseCase) {
    this.createCustomerUseCase = createCustomerUseCase;
    this.getCustomerByIdUseCase = getCustomerByIdUseCase;
    this.updateCustomerUseCase = updateCustomerUseCase;
    this.deleteCustomerUseCase = deleteCustomerUseCase;
    this.getCustomerByNameOrEmailUseCase = getCustomerByNameOrEmailUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public CustomerOutputDto postCustomer(@RequestBody @Valid CustomerInputDto customerInputDto) {
    var customer = customerInputDto.from(customerInputDto);
    var customerSaved = createCustomerUseCase.execute(customer);
    return CustomerOutputDto.toCustomerOutputDtoFrom(customerSaved);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public CustomerOutputDto getCustomerById(@PathVariable String id) {
    var customer = getCustomerByIdUseCase.execute(id);
    return CustomerOutputDto.toCustomerOutputDtoFrom(customer);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Override
  public CustomerOutputDto putCustomer(@PathVariable String id,
      @RequestBody @Valid CustomerInputDto customerInputDto) {
    var customer = customerInputDto.from(customerInputDto);
    var customerSaved = updateCustomerUseCase.execute(id, customer);
    return CustomerOutputDto.toCustomerOutputDtoFrom(customerSaved);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void deleteCustomer(@PathVariable String id) {
    deleteCustomerUseCase.execute(id);
  }

  @GetMapping("/name-email")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public Page<CustomerOutputDto> getCustomersByNameOrEmail(CustomerFilter customerFilter,
      @PageableDefault(sort = {"name"}) Pageable pageable) {
    var customersPage = getCustomerByNameOrEmailUseCase.execute(customerFilter.name(),
        customerFilter.email(), pageable);
    return !customersPage.getContent().isEmpty() ? CustomerOutputDto.toPage(customersPage)
        : Page.empty();
  }
}
