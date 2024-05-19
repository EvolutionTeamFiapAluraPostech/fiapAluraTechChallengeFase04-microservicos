package br.com.fiap.customer.application.usecase;

import br.com.fiap.customer.application.validator.DocNumberExistsValidator;
import br.com.fiap.customer.application.validator.DocNumberRequiredValidator;
import br.com.fiap.customer.application.validator.DocNumberTypeValidator;
import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.enums.DocNumberType;
import br.com.fiap.customer.domain.service.CustomerService;
import br.com.fiap.customer.infrastructure.httpclient.GetCoordinatesFromCepRequest;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CreateCustomerUseCase {

  public static final String LATITUDE = "Latitude";
  public static final String LONGITUDE = "Longitude";
  private final CustomerService customerService;
  private final DocNumberRequiredValidator docNumberRequiredValidator;
  private final DocNumberTypeValidator docNumberTypeValidator;
  private final DocNumberExistsValidator docNumberExistsValidator;
  private final GetCoordinatesFromCepRequest getCoordinatesFromCepRequest;

  public CreateCustomerUseCase(CustomerService customerService,
      DocNumberRequiredValidator docNumberRequiredValidator,
      DocNumberTypeValidator docNumberTypeValidator, DocNumberExistsValidator docNumberExistsValidator,
      GetCoordinatesFromCepRequest getCoordinatesFromCepRequest) {
    this.customerService = customerService;
    this.docNumberRequiredValidator = docNumberRequiredValidator;
    this.docNumberTypeValidator = docNumberTypeValidator;
    this.docNumberExistsValidator = docNumberExistsValidator;
    this.getCoordinatesFromCepRequest = getCoordinatesFromCepRequest;
  }

  @Transactional
  public Customer execute(Customer customer) {
    validateDocNumber(customer);
    if (isNecessaryGettingCoordinates(customer)) {
      getCoordinatesFromWebAndUpdateCompanyAddress(customer);
    }
    return customerService.save(customer);
  }

  private void getCoordinatesFromWebAndUpdateCompanyAddress(Customer customer) {
    var coordinates = getCoordinatesFromCepRequest.request(customer.getPostalCode());
    if (!coordinates.isEmpty()) {
      coordinates.forEach(coordinate -> {
        if (coordinate.containsKey(LATITUDE)) {
          customer.setLatitude(coordinate.get(LATITUDE));
        }
        if (coordinate.containsKey(LONGITUDE)) {
          customer.setLongitude(coordinate.get(LONGITUDE));
        }
      });
    }
  }

  private boolean isNecessaryGettingCoordinates(Customer customer) {
    return StringUtils.hasLength(customer.getPostalCode())
        && (customer.getLatitude() == null || customer.getLatitude().equals(BigDecimal.ZERO)
        || customer.getLongitude() == null || customer.getLongitude().equals(BigDecimal.ZERO));
  }

  private void validateDocNumber(Customer customer) {
    docNumberRequiredValidator.validate(customer.getDocNumber(), customer.getDocNumberType());
    docNumberTypeValidator.validate(customer.getDocNumber(), customer.getDocNumberType());
    docNumberExistsValidator.validate(customer.getDocNumber());
  }
}
