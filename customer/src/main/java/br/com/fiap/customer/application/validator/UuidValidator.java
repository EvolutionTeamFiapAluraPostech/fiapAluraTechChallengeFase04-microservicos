package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_ID_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.UUID_INVALID_MESSAGE;

import br.com.fiap.customer.shared.util.IsUUID;
import br.com.fiap.customer.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UuidValidator {

  public void validate(String uuid) {
    if (uuid == null || uuid.isBlank()) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
              UUID_INVALID_MESSAGE.formatted(uuid)));
    }
    if (!IsUUID.isUUID().matches(uuid)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), CUSTOMER_ID_FIELD,
              UUID_INVALID_MESSAGE.formatted(uuid)));
    }
  }
}
