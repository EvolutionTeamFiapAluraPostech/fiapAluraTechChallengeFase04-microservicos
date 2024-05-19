package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.fields.CustomerFields.CUSTOMER_DOC_NUMBER_FIELD;
import static br.com.fiap.customer.domain.messages.CustomerMessages.*;

import br.com.fiap.customer.domain.exception.ValidatorException;
import br.com.fiap.customer.domain.service.CustomerService;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class DocNumberAlreadyExistsInOtherCustomerValidator {

  private final CustomerService customerService;

  public DocNumberAlreadyExistsInOtherCustomerValidator(CustomerService customerService) {
    this.customerService = customerService;
  }

  public void validate(String docNumber, UUID customerId) {
    var optionalCustomer = customerService.findByDocNumber(docNumber);
    if (optionalCustomer.isPresent()) {
      var customer = optionalCustomer.get();
      if (!customer.getId().equals(customerId)) {
        throw new ValidatorException(
            new FieldError(this.getClass().getSimpleName(), CUSTOMER_DOC_NUMBER_FIELD,
                DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE.formatted(docNumber)));
      }
    }
  }
}
