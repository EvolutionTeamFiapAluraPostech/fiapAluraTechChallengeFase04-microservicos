package br.com.fiap.customer.application.usecase;

import static br.com.fiap.customer.shared.testdata.CustomerTestData.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class GetCustomerByNameOrEmailUseCaseTest {

  public static final int PAGE_NUMBER = 0;
  public static final int PAGE_SIZE = 1;
  @Mock
  private CustomerService customerService;
  @InjectMocks
  private GetCustomerByNameOrEmailUseCase getCustomerByNameOrEmailUseCase;

  @Test
  void shouldGetCustomerByName() {
    var customer = createCustomer();
    var customerName = customer.getName();
    var customerEmail = "";
    var customers = List.of(customer);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = customers.size();
    var page = new PageImpl<>(customers, pageable, size);

    when(customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = getCustomerByNameOrEmailUseCase.execute(customerName, customerEmail,
        pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldGetCustomerByEmail() {
    var customer = createCustomer();
    var customerName = "";
    var customerEmail = customer.getEmail();
    var customers = List.of(customer);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = customers.size();
    var page = new PageImpl<>(customers, pageable, size);

    when(customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = getCustomerByNameOrEmailUseCase.execute(customerName, customerEmail,
        pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldGetCustomerByNameAndEmail() {
    var customer = createCustomer();
    var customerName = customer.getName();
    var customerEmail = customer.getEmail();
    var customers = List.of(customer);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = customers.size();
    var page = new PageImpl<>(customers, pageable, size);

    when(customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = getCustomerByNameOrEmailUseCase.execute(customerName, customerEmail,
        pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldGetNothingWhenCustomerWasNotFoundByName() {
    var customer = createCustomer();
    var customerName = customer.getName();
    var customerEmail = "";
    var customers = new ArrayList<Customer>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(customers, pageable, size);

    when(customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = getCustomerByNameOrEmailUseCase.execute(customerName, customerEmail,
        pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldGetNothingWhenCustomerWasNotFoundByEmail() {
    var customer = createCustomer();
    var customerName = "";
    var customerEmail = customer.getEmail();
    var customers = new ArrayList<Customer>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(customers, pageable, size);

    when(customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = getCustomerByNameOrEmailUseCase.execute(customerName, customerEmail,
        pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldGetNothingWhenCustomerWasNotFoundByNameAndEmail() {
    var customer = createCustomer();
    var customerName = customer.getName();
    var customerEmail = customer.getEmail();
    var customers = new ArrayList<Customer>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(customers, pageable, size);

    when(customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = getCustomerByNameOrEmailUseCase.execute(customerName, customerEmail,
        pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }
}
