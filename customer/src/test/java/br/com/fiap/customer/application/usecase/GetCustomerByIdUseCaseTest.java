package br.com.fiap.customer.application.usecase;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_ID_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE;
import static br.com.fiap.customer.domain.messages.CustomerMessages.UUID_INVALID_MESSAGE;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.createCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.customer.application.validator.UuidValidator;
import br.com.fiap.customer.domain.exception.NoResultException;
import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
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
class GetCustomerByIdUseCaseTest {

  @Mock
  private CustomerService customerService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private GetCustomerByIdUseCase getCustomerByIdUseCase;

  @Test
  void shouldGetCustomerById() {
    var customer = createCustomer();
    when(customerService.findByIdRequired(customer.getId())).thenReturn(customer);

    var customerFound = getCustomerByIdUseCase.execute(customer.getId().toString());

    assertThat(customerFound).isNotNull();
    assertThat(customerFound.getId()).isNotNull().isEqualTo(customer.getId());
    verify(uuidValidator).validate(customer.getId().toString());
  }

  @Test
  void shouldThrowNoResultExceptionWhenCustomerWasNotFoundById() {
    var customer = createCustomer();
    when(customerService.findByIdRequired(customer.getId())).thenThrow(
        new NoResultException(new FieldError(this.getClass().getSimpleName(),
            CUSTOMER_ID_FIELD, CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(customer.getId()))));

    assertThatThrownBy(() -> getCustomerByIdUseCase.execute(customer.getId().toString()))
        .isInstanceOf(NoResultException.class)
        .hasMessage(CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE.formatted(customer.getId()));
    verify(uuidValidator).validate(customer.getId().toString());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1AB"})
  void shouldThrowExceptionWhenCustomerIdIsInvalid(String invalidUuid) {
    doThrow(new ValidatorException(
        new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
            UUID_INVALID_MESSAGE.formatted(invalidUuid))))
        .when(uuidValidator)
        .validate(invalidUuid);

    assertThatThrownBy(() -> getCustomerByIdUseCase.execute(invalidUuid))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(UUID_INVALID_MESSAGE.formatted(invalidUuid));
  }
}
