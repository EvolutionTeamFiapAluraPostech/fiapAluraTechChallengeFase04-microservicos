package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.messages.CustomerMessages.DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import br.com.fiap.customer.shared.testdata.CustomerTestData;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocNumberAlreadyExistsInOtherCustomerValidatorTest {

  @Mock
  private CustomerService customerService;
  @InjectMocks
  private DocNumberAlreadyExistsInOtherCustomerValidator docNumberAlreadyExistsInOtherCustomerValidator;

  @Test
  void shouldValidateDocNumber() {
    var customer = CustomerTestData.createCustomer();
    when(customerService.findByDocNumber(customer.getDocNumber())).thenReturn(
        Optional.of(customer));

    assertThatCode(
        () -> docNumberAlreadyExistsInOtherCustomerValidator.validate(customer.getDocNumber(),
            customer.getId())).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenDocNumberAlreadyExistsInOtherCustomer() {
    var customer = CustomerTestData.createCustomer();
    var customerId = UUID.randomUUID();
    when(customerService.findByDocNumber(customer.getDocNumber())).thenReturn(
        Optional.of(customer));

    assertThatThrownBy(
        () -> docNumberAlreadyExistsInOtherCustomerValidator.validate(customer.getDocNumber(),
            customerId))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE.formatted(
            customer.getDocNumber()));
  }
}
