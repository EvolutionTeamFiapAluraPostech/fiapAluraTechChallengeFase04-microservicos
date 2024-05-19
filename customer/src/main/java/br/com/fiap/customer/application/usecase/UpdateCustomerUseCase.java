package br.com.fiap.customer.application.usecase;

import br.com.fiap.customer.application.validator.DocNumberAlreadyExistsInOtherCustomerValidator;
import br.com.fiap.customer.application.validator.DocNumberRequiredValidator;
import br.com.fiap.customer.application.validator.DocNumberTypeValidator;
import br.com.fiap.customer.application.validator.UuidValidator;
import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.service.CustomerService;
import br.com.fiap.customer.infrastructure.httpclient.GetCoordinatesFromCepRequest;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UpdateCustomerUseCase {

  public static final String LATITUDE = "Latitude";
  public static final String LONGITUDE = "Longitude";
  private final CustomerService customerService;
  private final UuidValidator uuidValidator;
  private final DocNumberRequiredValidator docNumberRequiredValidator;
  private final DocNumberTypeValidator docNumberTypeValidator;
  private final DocNumberAlreadyExistsInOtherCustomerValidator docNumberAlreadyExistsInOtherCustomerValidator;
  private final GetCoordinatesFromCepRequest getCoordinatesFromCepRequest;

  public UpdateCustomerUseCase(CustomerService customerService, UuidValidator uuidValidator,
      DocNumberRequiredValidator docNumberRequiredValidator,
      DocNumberTypeValidator docNumberTypeValidator,
      DocNumberAlreadyExistsInOtherCustomerValidator docNumberAlreadyExistsInOtherCustomerValidator,
      GetCoordinatesFromCepRequest getCoordinatesFromCepRequest) {
    this.customerService = customerService;
    this.uuidValidator = uuidValidator;
    this.docNumberRequiredValidator = docNumberRequiredValidator;
    this.docNumberTypeValidator = docNumberTypeValidator;
    this.docNumberAlreadyExistsInOtherCustomerValidator = docNumberAlreadyExistsInOtherCustomerValidator;
    this.getCoordinatesFromCepRequest = getCoordinatesFromCepRequest;
  }

  @Transactional
  public Customer execute(String id, Customer customer) {
    uuidValidator.validate(id);
    customer.setId(UUID.fromString(id));
    var customerFound = customerService.findByIdRequired(UUID.fromString(id));
    validateDocNumber(customer);
    if (isNecessaryGettingCoordinates(customer)) {
      getCoordinatesFromWebAndUpdateCompanyAddress(customer);
    }
    updateCustomerFoundAttributesToSave(customerFound, customer);
    return customerService.save(customerFound);
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

  private void updateCustomerFoundAttributesToSave(Customer customerFound, Customer customer) {
    customerFound.setName(customer.getName());
    customerFound.setEmail(customer.getEmail());
    customerFound.setDocNumber(customer.getDocNumber());
    customerFound.setDocNumberType(customer.getDocNumberType());
    customerFound.setStreet(customer.getStreet());
    customerFound.setNumber(customer.getNumber());
    customerFound.setNeighborhood(customer.getNeighborhood());
    customerFound.setCity(customer.getCity());
    customerFound.setState(customer.getState());
    customerFound.setCountry(customer.getCountry());
    customerFound.setPostalCode(customer.getPostalCode());
    customerFound.setLatitude(customer.getLatitude());
    customerFound.setLongitude(customer.getLongitude());
  }

  private void validateDocNumber(Customer customer) {
    docNumberRequiredValidator.validate(customer.getDocNumber(), customer.getDocNumberType());
    docNumberTypeValidator.validate(customer.getDocNumber(), customer.getDocNumberType());
    docNumberAlreadyExistsInOtherCustomerValidator.validate(customer.getDocNumber(),
        customer.getId());
  }
}
