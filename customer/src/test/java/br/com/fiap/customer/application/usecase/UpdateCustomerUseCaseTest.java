package br.com.fiap.customer.application.usecase;

import static br.com.fiap.customer.domain.enums.DocNumberType.CNPJ;
import static br.com.fiap.customer.domain.enums.DocNumberType.CPF;
import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_DOC_NUMBER_FIELD;
import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_ID_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE;
import static br.com.fiap.customer.domain.messages.CustomerMessages.UUID_INVALID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.application.validator.DocNumberAlreadyExistsInOtherCustomerValidator;
import br.com.fiap.customer.application.validator.DocNumberRequiredValidator;
import br.com.fiap.customer.application.validator.DocNumberTypeValidator;
import br.com.fiap.customer.application.validator.UuidValidator;
import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import br.com.fiap.customer.shared.testdata.CustomerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseTest {

  @Mock
  private CustomerService customerService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private DocNumberRequiredValidator docNumberRequiredValidator;
  @Mock
  private DocNumberTypeValidator docNumberTypeValidator;
  @Mock
  private DocNumberAlreadyExistsInOtherCustomerValidator docNumberAlreadyExistsInOtherCustomerValidator;
  @InjectMocks
  private UpdateCustomerUseCase updateCustomerUseCase;

  @Test
  void shouldUpdateCustomer() {
    var customer = CustomerTestData.createCustomer();
    customer.setName(CustomerTestData.ALTERNATIVE_CUSTOMER_NAME);
    customer.setEmail(CustomerTestData.ALTERNATIVE_CUSTOMER_EMAIL);
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);
    when(customerService.save(customer)).thenReturn(customer);

    var customerSaved = updateCustomerUseCase.execute(customer.getId().toString(), customer);

    assertThat(customerSaved).isNotNull();
    assertThat(customerSaved.getId()).isNotNull();
    assertThat(customerSaved.getName()).isNotNull().isEqualTo(customer.getName());
    assertThat(customerSaved.getEmail()).isNotNull().isEqualTo(customer.getEmail());
    verify(uuidValidator).validate(customer.getId().toString());
    verify(docNumberRequiredValidator).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberTypeValidator).validate(customer.getDocNumber(), customer.getDocNumberType());
    verify(docNumberAlreadyExistsInOtherCustomerValidator).validate(customer.getDocNumber(),
        customer.getId());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1Ab"})
  void shouldThrowExceptionWhenCustomerIdIsInvalid(String customerId) {
    var customer = CustomerTestData.createCustomer();
    doThrow(new ValidatorException(
        new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
            UUID_INVALID_MESSAGE.formatted(customerId)))).when(uuidValidator)
        .validate(customerId);

    assertThatThrownBy(() -> updateCustomerUseCase.execute(customerId, customer))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(UUID_INVALID_MESSAGE.formatted(customerId));

    verify(uuidValidator).validate(customerId);
    verify(customerService, never()).findByIdRequired(customer.getId());
    verify(docNumberTypeValidator, never()).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberAlreadyExistsInOtherCustomerValidator, never()).validate(
        customer.getDocNumber(), customer.getId());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCpfDocNumberWasNotFilled(String cpf) {
    var customer = CustomerTestData.createCustomer();
    customer.setDocNumber(cpf);
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);
    doThrow(ValidatorException.class).when(docNumberRequiredValidator).validate(cpf, CPF);

    assertThatThrownBy(() -> updateCustomerUseCase.execute(customer.getId().toString(), customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberTypeValidator, never()).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberAlreadyExistsInOtherCustomerValidator, never()).validate(
        customer.getDocNumber(), customer.getId());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCnpjDocNumberWasNotFilled(String cnpj) {
    var customer = CustomerTestData.createCustomer();
    customer.setDocNumber(cnpj);
    customer.setDocNumberType(CNPJ);
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);
    doThrow(ValidatorException.class).when(docNumberRequiredValidator).validate(cnpj, CNPJ);

    assertThatThrownBy(() -> updateCustomerUseCase.execute(customer.getId().toString(), customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberTypeValidator, never()).validate(customer.getDocNumber(),
        customer.getDocNumberType());
    verify(docNumberAlreadyExistsInOtherCustomerValidator, never()).validate(
        customer.getDocNumber(), customer.getId());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678",
      "123456789", "1234567890", "59059270000110"})
  void shouldThrowExceptionWhenCpfDocNumberIsInvalid(String docNumber) {
    var customer = CustomerTestData.createCustomer();
    customer.setDocNumber(docNumber);
    customer.setDocNumberType(CPF);
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);
    doThrow(ValidatorException.class).when(docNumberTypeValidator)
        .validate(docNumber, customer.getDocNumberType());

    assertThatThrownBy(() -> updateCustomerUseCase.execute(customer.getId().toString(), customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberAlreadyExistsInOtherCustomerValidator, never()).validate(
        customer.getDocNumber(),
        customer.getId());
    verify(customerService, never()).save(customer);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678",
      "123456789", "1234567890", "59059270000110"})
  void shouldThrowExceptionWhenCnpjDocNumberIsInvalid(String docNumber) {
    var customer = CustomerTestData.createCustomer();
    customer.setDocNumber(docNumber);
    customer.setDocNumberType(CNPJ);
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);
    doThrow(ValidatorException.class).when(docNumberTypeValidator)
        .validate(docNumber, customer.getDocNumberType());

    assertThatThrownBy(() -> updateCustomerUseCase.execute(customer.getId().toString(), customer))
        .isInstanceOf(ValidatorException.class);

    verify(docNumberAlreadyExistsInOtherCustomerValidator, never()).validate(
        customer.getDocNumber(), customer.getId());
    verify(customerService, never()).save(customer);
  }

  @Test
  void shouldThrowExceptionWhenDocNumberAlreadyExists() {
    var customer = CustomerTestData.createCustomer();
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);
    doThrow(new ValidatorException(
        new FieldError(this.getClass().getSimpleName(), CUSTOMER_DOC_NUMBER_FIELD,
            DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE.formatted(
                customer.getDocNumber())))).when(docNumberAlreadyExistsInOtherCustomerValidator)
        .validate(customer.getDocNumber(), customer.getId());

    assertThatThrownBy(() -> updateCustomerUseCase.execute(customer.getId().toString(), customer))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE.formatted(
            customer.getDocNumber()));

    verify(uuidValidator).validate(customer.getId().toString());
    verify(docNumberTypeValidator).validate(customer.getDocNumber(), customer.getDocNumberType());
    verify(docNumberAlreadyExistsInOtherCustomerValidator).validate(
        customer.getDocNumber(), customer.getId());
    verify(customerService, never()).save(customer);
  }
}
