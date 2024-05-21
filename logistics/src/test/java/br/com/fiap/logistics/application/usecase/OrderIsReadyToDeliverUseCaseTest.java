package br.com.fiap.logistics.application.usecase;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.EM_ROTA_DE_ENTREGA;
import static br.com.fiap.logistics.domain.enums.LogisticEnum.ENTREGUE;
import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTICS_ID_FIELD;
import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTIC_STATUS_FIELD;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_NOT_FOUND_BY_ID_MESSAGE;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_WITH_INVALID_UUID_MESSAGE;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createLogistic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.logistics.application.validator.LogisticOrderAlreadyDeliveredValidator;
import br.com.fiap.logistics.application.validator.LogisticOrderIsInRouteToDeliverValidator;
import br.com.fiap.logistics.application.validator.UuidValidator;
import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.exception.NoResultException;
import br.com.fiap.logistics.domain.exception.ValidatorException;
import br.com.fiap.logistics.domain.service.LogisticService;
import br.com.fiap.logistics.infrastructure.httpclient.order.request.PutOrderAwaitingDeliveryHttpRequest;
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
class OrderIsReadyToDeliverUseCaseTest {

  @Mock
  private LogisticService logisticService;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private LogisticOrderAlreadyDeliveredValidator logisticOrderAlreadyDeliveredValidator;
  @Mock
  private LogisticOrderIsInRouteToDeliverValidator logisticOrderIsAlreadyInRouteToDeliverValidator;
  @Mock
  private PutOrderAwaitingDeliveryHttpRequest putOrderAwaitingDeliveryHttpRequest;
  @InjectMocks
  private OrderIsReadyToDeliverUseCase orderIsReadyToDeliverUseCase;

  @Test
  void shouldSetOrderAsReadyToDelivery() {
    var logistic = createLogistic();
    var id = logistic.getId();
    when(logisticService.findByIdRequired(id)).thenReturn(logistic);

    assertThatCode(() -> orderIsReadyToDeliverUseCase.execute(id.toString()))
        .doesNotThrowAnyException();
    assertThat(logistic.getStatus()).isEqualTo(EM_ROTA_DE_ENTREGA);
    verify(uuidValidator).validate(id.toString());
    verify(logisticOrderAlreadyDeliveredValidator).validate(logistic);
    verify(logisticOrderIsAlreadyInRouteToDeliverValidator).validate(logistic);
    verify(putOrderAwaitingDeliveryHttpRequest).request(logistic.getOrderId().toString());
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a", "1a#"})
  void shouldThrowExceptionWhenLogisticIdIsInvalid(String id) {
    doThrow(
        new ValidatorException(new FieldError(this.getClass().getSimpleName(), LOGISTICS_ID_FIELD,
            LOGISTICS_WITH_INVALID_UUID_MESSAGE.formatted(id))))
        .when(uuidValidator).validate(id);

    assertThatThrownBy(() -> orderIsReadyToDeliverUseCase.execute(id))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LOGISTICS_WITH_INVALID_UUID_MESSAGE.formatted(id));
    verify(logisticService, never()).findByIdRequired(any(UUID.class));
    verify(logisticOrderAlreadyDeliveredValidator, never()).validate(any(Logistic.class));
    verify(logisticOrderIsAlreadyInRouteToDeliverValidator, never()).validate(any(Logistic.class));
    verify(putOrderAwaitingDeliveryHttpRequest, never()).request(any(String.class));
  }

  @Test
  void shouldThrowExceptionWhenLogisticWasNotFound() {
    var id = UUID.randomUUID();
    when(logisticService.findByIdRequired(id)).thenThrow(
        new NoResultException(new FieldError(this.getClass().getSimpleName(),
            LOGISTICS_ID_FIELD, LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(id))));

    assertThatThrownBy(() -> orderIsReadyToDeliverUseCase.execute(id.toString()))
        .isInstanceOf(NoResultException.class)
        .hasMessage(LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(id));
    verify(uuidValidator).validate(id.toString());
    verify(logisticOrderAlreadyDeliveredValidator, never()).validate(any(Logistic.class));
    verify(logisticOrderIsAlreadyInRouteToDeliverValidator, never()).validate(any(Logistic.class));
    verify(putOrderAwaitingDeliveryHttpRequest, never()).request(any(String.class));
  }

  @Test
  void shouldThrowExceptionWhenLogisticWasAlreadyDelivered() {
    var logistic = createLogistic();
    logistic.setStatus(ENTREGUE);
    var id = logistic.getId();
    when(logisticService.findByIdRequired(id)).thenReturn(logistic);
    doThrow(new ValidatorException(new FieldError(this.getClass().getSimpleName(),
        LOGISTIC_STATUS_FIELD, LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE.formatted(id))))
        .when(logisticOrderAlreadyDeliveredValidator).validate(logistic);

    assertThatThrownBy(() -> orderIsReadyToDeliverUseCase.execute(id.toString()))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE.formatted(id));
    verify(uuidValidator).validate(id.toString());
    verify(logisticOrderIsAlreadyInRouteToDeliverValidator, never()).validate(any(Logistic.class));
    verify(putOrderAwaitingDeliveryHttpRequest, never()).request(any(String.class));
  }
}
