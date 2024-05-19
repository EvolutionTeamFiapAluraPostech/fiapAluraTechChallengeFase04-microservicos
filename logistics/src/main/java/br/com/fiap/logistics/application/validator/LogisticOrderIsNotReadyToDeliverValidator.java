package br.com.fiap.logistics.application.validator;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;
import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTIC_STATUS_FIELD;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTIC_ORDER_IS_NOT_READY_TO_DELIVER_MESSAGE;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class LogisticOrderIsNotReadyToDeliverValidator {

  public void validate(Logistic logistic) {
    if (logistic.getStatus().equals(ANALISE)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), LOGISTIC_STATUS_FIELD,
              LOGISTIC_ORDER_IS_NOT_READY_TO_DELIVER_MESSAGE.formatted(logistic.getStatus().name(),
                  logistic.getId())));
    }
  }
}
