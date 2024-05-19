package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_DOC_NUMBER_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.DOCUMENT_NUMBER_ALREADY_EXISTS_MESSAGE;

import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class DocNumberExistsValidator {

  private final CustomerService customerService;

  public DocNumberExistsValidator(CustomerService customerService) {
    this.customerService = customerService;
  }

  public void validate(String docNumber) {
    var docNumberExists = customerService.isCustomerDocNumberAlreadyExists(docNumber);
    if (docNumberExists) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          CUSTOMER_DOC_NUMBER_FIELD,
          DOCUMENT_NUMBER_ALREADY_EXISTS_MESSAGE.formatted(docNumber)));
    }
  }
}
