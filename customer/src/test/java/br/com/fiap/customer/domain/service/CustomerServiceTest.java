package br.com.fiap.customer.domain.service;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_ID_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.exception.NoResultException;
import br.com.fiap.customer.infrastructure.repository.CustomerRepository;
import br.com.fiap.customer.shared.testdata.CustomerTestData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  public static final int PAGE_NUMBER = 0;
  public static final int PAGE_SIZE = 1;
  @Mock
  private CustomerRepository customerRepository;
  @InjectMocks
  private CustomerService customerService;

  @Test
  void shouldSaveCustomer() {
    var customer = CustomerTestData.createNewCustomer();
    var customerWithId = CustomerTestData.createNewCustomer();
    customerWithId.setId(UUID.randomUUID());
    when(customerRepository.save(customer)).thenReturn(customerWithId);

    var customerSaved = customerService.save(customer);

    assertThat(customerSaved).isNotNull();
    assertThat(customerSaved.getId()).isNotNull();
  }

  @Test
  void shouldFindCustomerByDocNumber() {
    var customerWithId = CustomerTestData.createNewCustomer();
    customerWithId.setId(UUID.randomUUID());
    when(customerRepository.findByDocNumber(customerWithId.getDocNumber())).thenReturn(
        Optional.of(customerWithId));

    var customer = customerService.findByDocNumber(customerWithId.getDocNumber());

    assertThat(customer).isPresent();
    assertThat(customer.get().getId()).isNotNull().isEqualTo(customerWithId.getId());
  }

  @Test
  void shouldReturnEmptyWhenNotFindCustomerByDocNumber() {
    var customerWithId = CustomerTestData.createNewCustomer();
    customerWithId.setId(UUID.randomUUID());
    when(customerRepository.findByDocNumber(customerWithId.getDocNumber())).thenReturn(
        Optional.empty());

    var customer = customerService.findByDocNumber(customerWithId.getDocNumber());

    assertThat(customer).isNotPresent();
  }

  @Test
  void shouldReturnTrueWhenCustomerDocNumberAlreadyExists() {
    var customerWithId = CustomerTestData.createNewCustomer();
    customerWithId.setId(UUID.randomUUID());
    when(customerRepository.findByDocNumber(customerWithId.getDocNumber())).thenReturn(
        Optional.of(customerWithId));

    var isCustomerAlreadyExists = customerService.isCustomerDocNumberAlreadyExists(
        customerWithId.getDocNumber());

    assertThat(isCustomerAlreadyExists).isTrue();
  }

  @Test
  void shouldReturnFalseWhenCustomerDocNumberDoesNotAlreadyExist() {
    var customerWithId = CustomerTestData.createNewCustomer();
    customerWithId.setId(UUID.randomUUID());
    when(customerRepository.findByDocNumber(customerWithId.getDocNumber())).thenReturn(
        Optional.empty());

    var isCustomerAlreadyExists = customerService.isCustomerDocNumberAlreadyExists(
        customerWithId.getDocNumber());

    assertThat(isCustomerAlreadyExists).isFalse();
  }

  @Test
  void shouldFindCustomerById() {
    var customer = CustomerTestData.createNewCustomer();
    customer.setId(UUID.randomUUID());
    when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

    var customerFound = customerService.findByIdRequired(customer.getId());

    assertThat(customerFound).isNotNull();
    assertThat(customerFound.getId()).isNotNull().isEqualTo(customer.getId());
  }

  @Test
  void shouldThrowExceptionWhenCustomerWasNotFoundById() {
    var customer = CustomerTestData.createNewCustomer();
    customer.setId(UUID.randomUUID());
    when(customerRepository.findById(customer.getId())).thenThrow(
        new NoResultException(new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
            CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(customer.getId()))));

    assertThatThrownBy(() -> customerService.findByIdRequired(customer.getId()))
        .isInstanceOf(NoResultException.class)
        .hasMessage(CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(customer.getId()));
  }

  @Test
  void shouldFindCustomerByNameAndEmailWhenCustomerExists() {
    var customer = createCustomer();
    var customerName = customer.getName();
    var customerEmail = customer.getEmail();
    var customers = List.of(customer);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = customers.size();
    var page = new PageImpl<>(customers, pageable, size);

    when(customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName,
        customerEmail, pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldFindCustomerByNameWhenCustomerExists() {
    var customer = createCustomer();
    var customerName = customer.getName();
    var customerEmail = "";
    var customers = List.of(customer);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = customers.size();
    var page = new PageImpl<>(customers, pageable, size);

    when(customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName,
        customerEmail, pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldFindCustomerByEmailWhenCustomerExists() {
    var customer = createCustomer();
    var customerName = "";
    var customerEmail = customer.getEmail();
    var customers = List.of(customer);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = customers.size();
    var page = new PageImpl<>(customers, pageable, size);

    when(customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName,
        customerEmail, pageable);

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

    when(customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName,
        customerEmail, pageable);

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

    when(customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName,
        customerEmail, pageable);

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

    when(customerRepository.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName, customerEmail,
        pageable)).thenReturn(page);
    var customersFound = customerService.queryCustomersByNameLikeIgnoreCaseOrEmail(customerName,
        customerEmail, pageable);

    assertThat(customersFound).isNotNull();
    assertThat(customersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(customersFound.getTotalPages()).isEqualTo(size);
    assertThat(customersFound.getTotalElements()).isEqualTo(size);
  }
}
