package br.com.fiap.customer.application.usecase;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_ID_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE;
import static br.com.fiap.customer.domain.messages.CustomerMessages.UUID_INVALID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.application.validator.UuidValidator;
import br.com.fiap.customer.domain.exception.NoResultException;
import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import br.com.fiap.customer.shared.testdata.CustomerTestData;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseTest {

  @Mock
  private CustomerService customerService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private DeleteCustomerUseCase deleteCustomerUseCase;

  @Test
  void shouldDeleteCustomer() {
    var customer = CustomerTestData.createCustomer();
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);

    assertThatCode(() -> deleteCustomerUseCase.execute(customer.getId().toString()))
        .doesNotThrowAnyException();
    verify(uuidValidator).validate(customer.getId().toString());
  }

  @ParameterizedTest
  @ValueSource(strings = {"1Ab", "ABC"})
  void shouldThrowExceptionWhenCustomerIdIsInvalid(String customerId) {
    doThrow(new ValidatorException(
        new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
            UUID_INVALID_MESSAGE.formatted(customerId)))).when(uuidValidator).validate(customerId);

    assertThatThrownBy(() -> deleteCustomerUseCase.execute(customerId))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(UUID_INVALID_MESSAGE.formatted(customerId));
    verify(customerService, never()).findByIdRequired(any(UUID.class));
  }

  @Test
  void shouldThrowNotFoundExceptionWhenCustomerWasNotFoundById() {
    var id = UUID.randomUUID();
    doThrow(new NoResultException(
        new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
            CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(id.toString()))))
        .when(customerService).findByIdRequired(id);

    assertThatThrownBy(() -> deleteCustomerUseCase.execute(id.toString()))
        .isInstanceOf(NoResultException.class)
        .hasMessage(CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(id.toString()));
    verify(uuidValidator).validate(id.toString());
  }
}
