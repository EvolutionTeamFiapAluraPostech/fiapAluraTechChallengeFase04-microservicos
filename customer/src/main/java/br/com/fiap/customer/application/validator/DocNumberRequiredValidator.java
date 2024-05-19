package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_DOC_NUMBER_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.ENTER_DOCUMENT_NUMBER_MESSAGE;

import br.com.fiap.customer.domain.enums.DocNumberType;
import br.com.fiap.customer.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class DocNumberRequiredValidator {

  public void validate(String docNumber, DocNumberType docNumberType) {
    if (docNumber == null || docNumber.isBlank() || docNumberType == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          CUSTOMER_DOC_NUMBER_FIELD,
          ENTER_DOCUMENT_NUMBER_MESSAGE));
    }
  }
}
