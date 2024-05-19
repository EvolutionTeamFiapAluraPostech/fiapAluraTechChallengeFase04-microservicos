package br.com.fiap.logistics.application.validator;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;
import static br.com.fiap.logistics.domain.enums.LogisticEnum.EM_ROTA_DE_ENTREGA;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTIC_ORDER_IS_ALREADY_IN_ROUTE_TO_DELIVER_MESSAGE;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createLogistic;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.logistics.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogisticOrderIsInRouteToDeliverValidatorTest {

  @Spy
  private LogisticOrderIsInRouteToDeliverValidator logisticOrderIsInRouteToDeliverValidator;

  @Test
  void shouldValidateLogisticOrderNotInRouteToDeliver() {
    var logistic = createLogistic();
    logistic.setStatus(ANALISE);

    assertThatCode(() -> logisticOrderIsInRouteToDeliverValidator.validate(logistic))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(strings = {"EM_"})
  void shouldThrowExceptionWhenLogisticOrderAlreadyDelivered() {
    var logistic = createLogistic();
    logistic.setStatus(EM_ROTA_DE_ENTREGA);

    assertThatThrownBy(() -> logisticOrderIsInRouteToDeliverValidator.validate(logistic))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LOGISTIC_ORDER_IS_ALREADY_IN_ROUTE_TO_DELIVER_MESSAGE.formatted(logistic.getId()));
  }
}