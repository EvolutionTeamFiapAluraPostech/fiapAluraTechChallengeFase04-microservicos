package br.com.fiap.logistics.application.validator;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ENTREGUE;
import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTIC_STATUS_FIELD;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class LogisticOrderAlreadyDeliveredValidator {

  public void validate(Logistic logistic) {
    if (logistic.getStatus().equals(ENTREGUE)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), LOGISTIC_STATUS_FIELD,
              LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE.formatted(logistic.getId())));
    }
  }
}
