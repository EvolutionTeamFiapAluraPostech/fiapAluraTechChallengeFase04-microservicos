package br.com.fiap.customer.shared.testdata;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.enums.DocNumberType;
import java.math.BigDecimal;
import java.util.UUID;

public final class CustomerTestData {

  public static final String DEFAULT_CUSTOMER_NAME = "Thomas Anderson";
  public static final String DEFAULT_CUSTOMER_EMAIL = "thomas.anderson@itcompany.com";
  public static final String DEFAULT_CUSTOMER_CPF_DOC_NUMBER = "11955975094";
  public static final String DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER = "59059270000110";
  public static final String DEFAULT_CUSTOMER_STREET = "Av. Lins de Vasconcelos";
  public static final String DEFAULT_CUSTOMER_ADDRESS_NUMBER = "1222";
  public static final String DEFAULT_CUSTOMER_NEIGHBORHOOD = "Cambuci";
  public static final String DEFAULT_CUSTOMER_CITY = "São Paulo";
  public static final String DEFAULT_CUSTOMER_STATE = "SP";
  public static final String DEFAULT_CUSTOMER_COUNTRY = "Brasil";
  public static final String DEFAULT_CUSTOMER_POSTAL_CODE = "01538001";
  public static final String ALTERNATIVE_CUSTOMER_NAME = "Neo";
  public static final String ALTERNATIVE_CUSTOMER_EMAIL = "neo@matrix.com";
  public static final String ALTERNATIVE_CUSTOMER_CPF_DOC_NUMBER = "64990860020";
  public static final BigDecimal DEFAULT_COMPANY_LATITUDE = new BigDecimal("-23.56388");
  public static final BigDecimal DEFAULT_COMPANY_LONGITUDE = new BigDecimal("-46.65241");


  public static Customer createNewCustomer() {
    return Customer.builder()
        .active(true)
        .name(DEFAULT_CUSTOMER_NAME)
        .email(DEFAULT_CUSTOMER_EMAIL)
        .docNumber(DEFAULT_CUSTOMER_CPF_DOC_NUMBER)
        .docNumberType(DocNumberType.CPF)
        .street(DEFAULT_CUSTOMER_STREET)
        .number(DEFAULT_CUSTOMER_ADDRESS_NUMBER)
        .neighborhood(DEFAULT_CUSTOMER_NEIGHBORHOOD)
        .city(DEFAULT_CUSTOMER_CITY)
        .state(DEFAULT_CUSTOMER_STATE)
        .country(DEFAULT_CUSTOMER_COUNTRY)
        .postalCode(DEFAULT_CUSTOMER_POSTAL_CODE)
        .latitude(DEFAULT_COMPANY_LATITUDE)
        .longitude(DEFAULT_COMPANY_LONGITUDE)
        .build();
  }

  public static Customer createCustomer() {
    var customer = createNewCustomer();
    customer.setId(UUID.randomUUID());
    return customer;
  }

  private CustomerTestData(){
  }
}
