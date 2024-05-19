package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.messages.CustomerMessages.DOCUMENT_NUMBER_ALREADY_EXISTS_MESSAGE;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF_DOC_NUMBER;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocNumberExistsValidatorTest {

  @Mock
  private CustomerService customerService;
  @InjectMocks
  private DocNumberExistsValidator docNumberExistsValidator;

  @Test
  void shouldValidateDocNumberWhenItDoesNotExist() {
    when(customerService.isCustomerDocNumberAlreadyExists(DEFAULT_CUSTOMER_CPF_DOC_NUMBER))
        .thenReturn(Boolean.FALSE);

    assertThatCode(() -> docNumberExistsValidator.validate(DEFAULT_CUSTOMER_CPF_DOC_NUMBER))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenDocNumberAlreadyExists() {
    when(customerService.isCustomerDocNumberAlreadyExists(DEFAULT_CUSTOMER_CPF_DOC_NUMBER))
        .thenReturn(Boolean.TRUE);

    assertThatThrownBy(() -> docNumberExistsValidator.validate(DEFAULT_CUSTOMER_CPF_DOC_NUMBER))
        .isInstanceOf(ValidatorException.class)
        .hasMessageContaining(DOCUMENT_NUMBER_ALREADY_EXISTS_MESSAGE
            .formatted(DEFAULT_CUSTOMER_CPF_DOC_NUMBER));
  }
}
