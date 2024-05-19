package br.com.fiap.logistics.application.validator;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ENTREGUE;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createLogistic;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.logistics.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogisticOrderAlreadyDeliveredValidatorTest {

  @Spy
  private LogisticOrderAlreadyDeliveredValidator logisticOrderAlreadyDeliveredValidator;

  @Test
  void shouldValidateLogisticOrderNotDelivered() {
    var logistic = createLogistic();

    assertThatCode(() -> logisticOrderAlreadyDeliveredValidator.validate(logistic))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenLogisticOrderAlreadyDelivered() {
    var logistic = createLogistic();
    logistic.setStatus(ENTREGUE);

    assertThatThrownBy(() -> logisticOrderAlreadyDeliveredValidator.validate(logistic))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE.formatted(logistic.getId()));
  }
}
