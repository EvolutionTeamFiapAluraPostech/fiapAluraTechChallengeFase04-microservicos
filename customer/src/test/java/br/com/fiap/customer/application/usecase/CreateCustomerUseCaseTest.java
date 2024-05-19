package br.com.fiap.customer.application.usecase;

import static br.com.fiap.customer.domain.enums.DocNumberType.CNPJ;
import static br.com.fiap.customer.domain.enums.DocNumberType.CPF;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.application.validator.DocNumberExistsValidator;
import br.com.fiap.customer.application.validator.DocNumberRequiredValidator;
import br.com.fiap.customer.application.validator.DocNumberTypeValidator;
import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import br.com.fiap.customer.shared.testdata.CustomerTestData;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {

  @Mock
  private CustomerService customerService;
  @Mock
  private DocNumberRequiredValidator docNumberRequiredValidator;
  @Mock
  private DocNumberTypeValidator docNumberTypeValidator;
  @Mock
  private DocNumberExistsValidator docNumberExistsValidator;
  @InjectMocks
  private CreateCustomerUseCase createCustomerUseCase;

  @Test
  void shouldCreateCustomer() {
    var customer = CustomerTestData.createNewCustomer();
    var customerWithId = CustomerTestData.createNewCustomer();
    customerWithId.setId(UUID.randomUUID());
    when(customerService.save(customer)).thenReturn(customerWithId);

    var customerSaved = createCustomerUseCase.execute(customer);

    assertThat(customerSaved).isNotNull();
    assertThat(customerSaved.getId()).isNotNull();
    verify(docNumberRequiredValidator).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberTypeValidator).validate(customer.getDocNumber(), customer.getDocNumberType());
    verify(docNumberExistsValidator).validate(customer.getDocNumber());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCpfDocNumberWasNotFilled(String cpf) {
    var customer = CustomerTestData.createNewCustomer();
    customer.setDocNumber(cpf);
    doThrow(ValidatorException.class).when(docNumberRequiredValidator).validate(cpf, CPF);

    assertThatThrownBy(() -> createCustomerUseCase.execute(customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberTypeValidator, never()).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberExistsValidator, never()).validate(customer.getDocNumber());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCnpjDocNumberWasNotFilled(String cnpj) {
    var customer = CustomerTestData.createNewCustomer();
    customer.setDocNumber(cnpj);
    customer.setDocNumberType(CNPJ);
    doThrow(ValidatorException.class).when(docNumberRequiredValidator).validate(cnpj, CNPJ);

    assertThatThrownBy(() -> createCustomerUseCase.execute(customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberTypeValidator, never()).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberExistsValidator, never()).validate(customer.getDocNumber());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678",
      "123456789", "1234567890", "59059270000110"})
  void shouldThrowExceptionWhenCpfDocNumberIsInvalid(String docNumber) {
    var customer = CustomerTestData.createNewCustomer();
    customer.setDocNumber(docNumber);
    customer.setDocNumberType(CPF);
    doThrow(ValidatorException.class).when(docNumberTypeValidator).validate(docNumber,
        customer.getDocNumberType());

    assertThatThrownBy(() -> createCustomerUseCase.execute(customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberExistsValidator, never()).validate(customer.getDocNumber());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678",
      "123456789", "1234567890", "59059270000110"})
  void shouldThrowExceptionWhenCnpjDocNumberIsInvalid(String docNumber) {
    var customer = CustomerTestData.createNewCustomer();
    customer.setDocNumber(docNumber);
    customer.setDocNumberType(CNPJ);
    doThrow(ValidatorException.class).when(docNumberTypeValidator).validate(docNumber,
        customer.getDocNumberType());

    assertThatThrownBy(() -> createCustomerUseCase.execute(customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberExistsValidator, never()).validate(customer.getDocNumber());
    verify(customerService, never()).save(customer);
  }

  @Test
  void shouldThrowExceptionWhenDocNumberAlreadyExists() {
    var customer = CustomerTestData.createNewCustomer();
    customer.setDocNumber(DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER);
    customer.setDocNumberType(CNPJ);
    doThrow(ValidatorException.class).when(docNumberExistsValidator).validate(
        customer.getDocNumber());

    assertThatThrownBy(() -> createCustomerUseCase.execute(customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberTypeValidator).validate(customer.getDocNumber(), customer.getDocNumberType());
    verify(docNumberExistsValidator).validate(customer.getDocNumber());
    verify(customerService, never()).save(customer);
  }
}
