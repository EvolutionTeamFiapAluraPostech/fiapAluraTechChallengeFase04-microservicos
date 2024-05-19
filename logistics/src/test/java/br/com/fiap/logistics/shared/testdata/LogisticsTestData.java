package br.com.fiap.logistics.shared.testdata;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.entity.LogisticItem;
import br.com.fiap.logistics.presentation.api.dto.LogisticsOrderInputDto;
import br.com.fiap.logistics.presentation.api.dto.LogisticsOrderItemInputDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class LogisticsTestData {

  public static final UUID DEFAULT_ORDER_UUID = UUID.randomUUID();
  public static final String DEFAULT_ORDER_ID = DEFAULT_ORDER_UUID.toString();
  public static final UUID DEFAULT_ORDER_ITEM_UUID = UUID.randomUUID();
  public static final String DEFAULT_ORDER_ITEM_ID = DEFAULT_ORDER_ITEM_UUID.toString();
  public static final UUID DEFAULT_COMPANY_UUID = UUID.fromString(
      "dcd3398e-4988-4fba-b8c0-a649ae1ff677");
  public static final String DEFAULT_COMPANY_ID = DEFAULT_COMPANY_UUID.toString();
  public static final UUID DEFAULT_CUSTOMER_UUID = UUID.fromString(
      "64f6db0a-3d9a-429c-a7e6-04c4691f3be9");
  public static final String DEFAULT_CUSTOMER_ID = DEFAULT_CUSTOMER_UUID.toString();
  public static final UUID DEFAULT_PRODUCT_UUID = UUID.fromString(
      "cfa8315f-3f9a-4105-a2f2-f02a0a303b20");
  public static final String DEFAULT_PRODUCT_ID = DEFAULT_PRODUCT_UUID.toString();
  public static final BigDecimal DEFAULT_PRODUCT_QUANTITY = BigDecimal.TEN;
  public static final BigDecimal DEFAULT_PRODUCT_PRICE = new BigDecimal("315");
  public static final String DEFAULT_PRODUCT_SKU = "Key/BR-/Erg/Bla";
  public static final String DEFAULT_PRODUCT_DESCRIPTION = "Keyboard Ergonomic Black";


  public static final String DEFAULT_COMPANY_NAME = "Matrix Company";
  public static final String DEFAULT_COMPANY_EMAIL = "matrix@matrix.com";
  public static final String DEFAULT_COMPANY_CNPJ_DOC_NUMBER = "41404629000184";
  public static final String DEFAULT_COMPANY_DOC_NUMBER_TYPE = "CNPJ";
  public static final String DEFAULT_COMPANY_STREET = "Alameda Rio Claro";
  public static final String DEFAULT_COMPANY_ADDRESS_NUMBER = "190";
  public static final String DEFAULT_COMPANY_NEIGHBORHOOD = "Bela Vista";
  public static final String DEFAULT_COMPANY_CITY = "São Paulo";
  public static final String DEFAULT_COMPANY_STATE = "SP";
  public static final String DEFAULT_COMPANY_COUNTRY = "Brasil";
  public static final String DEFAULT_COMPANY_POSTAL_CODE = "01332010";
  public static final BigDecimal DEFAULT_COMPANY_LATITUDE = new BigDecimal("-23.56388");
  public static final BigDecimal DEFAULT_COMPANY_LONGITUDE = new BigDecimal("-46.65241");

  public static final String DEFAULT_CUSTOMER_NAME = "Thomas Anderson";
  public static final String DEFAULT_CUSTOMER_EMAIL = "thomas.anderson@itcompany.com";
  public static final String DEFAULT_CUSTOMER_CPF_DOC_NUMBER = "11955975094";
  public static final String DEFAULT_CUSTOMER_DOC_NUMBER_TYPE = "CPF";
  public static final String DEFAULT_CUSTOMER_STREET = "Av. Lins de Vasconcelos";
  public static final String DEFAULT_CUSTOMER_ADDRESS_NUMBER = "1222";
  public static final String DEFAULT_CUSTOMER_NEIGHBORHOOD = "Cambuci";
  public static final String DEFAULT_CUSTOMER_CITY = "São Paulo";
  public static final String DEFAULT_CUSTOMER_STATE = "SP";
  public static final String DEFAULT_CUSTOMER_COUNTRY = "Brasil";
  public static final String DEFAULT_CUSTOMER_POSTAL_CODE = "01538001";

  public static Logistic createNewLogistic() {
    var logisticItems = new ArrayList<LogisticItem>();

    var logistic = Logistic.builder()
        .companyId(DEFAULT_COMPANY_UUID)
        .companyName(DEFAULT_COMPANY_NAME)
        .companyEmail(DEFAULT_COMPANY_EMAIL)
        .companyDocNumber(DEFAULT_COMPANY_CNPJ_DOC_NUMBER)
        .companyDocNumberType(DEFAULT_COMPANY_DOC_NUMBER_TYPE)
        .companyStreet(DEFAULT_COMPANY_STREET)
        .companyNumber(DEFAULT_COMPANY_ADDRESS_NUMBER)
        .companyNeighborhood(DEFAULT_COMPANY_NEIGHBORHOOD)
        .companyCity(DEFAULT_COMPANY_CITY)
        .companyState(DEFAULT_COMPANY_STATE)
        .companyCountry(DEFAULT_COMPANY_COUNTRY)
        .companyPostalCode(DEFAULT_COMPANY_POSTAL_CODE)
        .orderId(DEFAULT_ORDER_UUID)
        .customerId(DEFAULT_CUSTOMER_UUID)
        .customerName(DEFAULT_CUSTOMER_NAME)
        .customerEmail(DEFAULT_CUSTOMER_EMAIL)
        .customerDocNumber(DEFAULT_CUSTOMER_CPF_DOC_NUMBER)
        .customerDocNumberType(DEFAULT_CUSTOMER_DOC_NUMBER_TYPE)
        .customerStreet(DEFAULT_CUSTOMER_STREET)
        .customerNumber(DEFAULT_CUSTOMER_ADDRESS_NUMBER)
        .customerNeighborhood(DEFAULT_CUSTOMER_NEIGHBORHOOD)
        .customerCity(DEFAULT_CUSTOMER_CITY)
        .customerState(DEFAULT_CUSTOMER_STATE)
        .customerCountry(DEFAULT_CUSTOMER_COUNTRY)
        .customerPostalCode(DEFAULT_CUSTOMER_POSTAL_CODE)
        .status(ANALISE)
        .logisticsItems(logisticItems)
        .build();

    var logisticItem = LogisticItem.builder()
        .orderItemId(DEFAULT_ORDER_ITEM_UUID)
        .productId(DEFAULT_PRODUCT_UUID)
        .productSku(DEFAULT_PRODUCT_SKU)
        .productDescription(DEFAULT_PRODUCT_DESCRIPTION)
        .quantity(DEFAULT_PRODUCT_QUANTITY)
        .price(DEFAULT_PRODUCT_PRICE)
        .build();
    logisticItems.add(logisticItem);

    return logistic;
  }

  public static Logistic createLogistic() {
    var logistics = createNewLogistic();
    logistics.setId(UUID.randomUUID());
    return logistics;
  }

  public static LogisticsOrderInputDto createNewLogisticDto() {
    var logisticItemsInputDto = new ArrayList<LogisticsOrderItemInputDto>();

    var logisticsOrderInputDto = new LogisticsOrderInputDto(
        DEFAULT_COMPANY_ID,
        DEFAULT_COMPANY_NAME,
        DEFAULT_COMPANY_EMAIL,
        DEFAULT_COMPANY_CNPJ_DOC_NUMBER,
        DEFAULT_COMPANY_DOC_NUMBER_TYPE,
        DEFAULT_COMPANY_STREET,
        DEFAULT_COMPANY_ADDRESS_NUMBER,
        DEFAULT_COMPANY_NEIGHBORHOOD,
        DEFAULT_COMPANY_CITY,
        DEFAULT_COMPANY_STATE,
        DEFAULT_COMPANY_COUNTRY,
        DEFAULT_COMPANY_POSTAL_CODE,
        DEFAULT_COMPANY_LATITUDE,
        DEFAULT_COMPANY_LONGITUDE,
        DEFAULT_ORDER_ID,
        DEFAULT_CUSTOMER_ID,
        DEFAULT_CUSTOMER_NAME,
        DEFAULT_CUSTOMER_EMAIL,
        DEFAULT_CUSTOMER_CPF_DOC_NUMBER,
        DEFAULT_CUSTOMER_DOC_NUMBER_TYPE,
        DEFAULT_CUSTOMER_STREET,
        DEFAULT_CUSTOMER_ADDRESS_NUMBER,
        DEFAULT_CUSTOMER_NEIGHBORHOOD,
        DEFAULT_CUSTOMER_CITY,
        DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY,
        DEFAULT_CUSTOMER_POSTAL_CODE,
        DEFAULT_COMPANY_LATITUDE,
        DEFAULT_COMPANY_LONGITUDE,
        logisticItemsInputDto);

    var logisticItemInputDto = new LogisticsOrderItemInputDto(DEFAULT_ORDER_ITEM_ID,
        DEFAULT_PRODUCT_ID,
        DEFAULT_PRODUCT_SKU,
        DEFAULT_PRODUCT_DESCRIPTION,
        DEFAULT_PRODUCT_QUANTITY,
        DEFAULT_PRODUCT_PRICE);
    logisticItemsInputDto.add(logisticItemInputDto);

    return logisticsOrderInputDto;
  }
}
