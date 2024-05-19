package br.com.fiap.logistics.application.validator;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;
import static br.com.fiap.logistics.domain.enums.LogisticEnum.EM_ROTA_DE_ENTREGA;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTIC_ORDER_IS_NOT_READY_TO_DELIVER_MESSAGE;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createLogistic;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.logistics.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogisticOrderIsNotReadyToDeliverValidatorTest {

  @Spy
  private LogisticOrderIsNotReadyToDeliverValidator logisticOrderIsNotReadyToDeliverValidator;

  @Test
  void shouldValidateLogisticOrderNotDelivered() {
    var logistic = createLogistic();
    logistic.setStatus(EM_ROTA_DE_ENTREGA);

    assertThatCode(() -> logisticOrderIsNotReadyToDeliverValidator.validate(logistic))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenLogisticOrderAlreadyDelivered() {
    var logistic = createLogistic();
    logistic.setStatus(ANALISE);

    assertThatThrownBy(() -> logisticOrderIsNotReadyToDeliverValidator.validate(logistic))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LOGISTIC_ORDER_IS_NOT_READY_TO_DELIVER_MESSAGE.formatted(logistic.getStatus(),
            logistic.getId()));
  }
}
