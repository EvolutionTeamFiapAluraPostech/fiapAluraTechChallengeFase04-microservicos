package br.com.fiap.customer.presentation.api;

import static br.com.fiap.customer.shared.util.IsUUID.isUUID;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.ALTERNATIVE_CUSTOMER_CPF_DOC_NUMBER;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.ALTERNATIVE_CUSTOMER_EMAIL;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.ALTERNATIVE_CUSTOMER_NAME;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF_DOC_NUMBER;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.createCustomer;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.createNewCustomer;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.customer.shared.util.StringUtil;
import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.domain.enums.DocNumberType;
import br.com.fiap.customer.shared.annotation.DatabaseTest;
import br.com.fiap.customer.shared.annotation.IntegrationTest;
import br.com.fiap.customer.shared.api.JsonUtil;
import br.com.fiap.customer.shared.testdata.CustomerTestData;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class PutCustomerApiTest {

  private static final String URL_CUSTOMERS = "/customers/{id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PutCustomerApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private Customer createAndPersistNewCustomer() {
    var customer = createNewCustomer();
    return entityManager.merge(customer);
  }

  private Customer getCustomerFoundById(String id) {
    return entityManager.find(Customer.class, UUID.fromString(id));
  }

  private Customer createAndPersistOtherCustomer() {
    var customer = createNewCustomer();
    customer.setDocNumber(ALTERNATIVE_CUSTOMER_CPF_DOC_NUMBER);
    return entityManager.merge(customer);
  }

  @Test
  void shouldPutCustomer() throws Exception {
    var customer = createAndPersistNewCustomer();
    customer.setName(ALTERNATIVE_CUSTOMER_NAME);
    customer.setEmail(ALTERNATIVE_CUSTOMER_EMAIL);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    var mvcResult = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isAccepted())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var customerFound = getCustomerFoundById(id);
    assertThat(customerFound).isNotNull();
    assertThat(customerFound.getId()).isNotNull().isEqualTo(customer.getId());
    assertThat(customerFound.getName()).isNotNull().isEqualTo(customer.getName());
    assertThat(customerFound.getDocNumber()).isNotNull().isEqualTo(customer.getDocNumber());
    assertThat(customerFound.getDocNumberType()).isNotNull().isEqualTo(customer.getDocNumberType());
    assertThat(customerFound.getEmail()).isNotNull().isEqualTo(customer.getEmail());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerNameWasNotFilled(String customerName) throws Exception {
    var customer = createAndPersistNewCustomer();
    customer.setName(customerName);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerNameLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerName = "AB";
    customer.setName(customerName);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerNameLengthIsBiggerThan500Characters() throws Exception {
    var customer = createCustomer();
    var customerName = StringUtil.generateStringLength(501);
    customer.setName(customerName);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerEmailWasNotFilled(String customerEmail) throws Exception {
    var customer = createCustomer();
    customer.setEmail(customerEmail);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"email.domain.com", " email.domain.com", "@", "1", "A@b@c@example.com",
      "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", "email @example.com"})
  void shouldReturnBadRequestWhenCustomerEmailWasInvalid(String customerEmail) throws Exception {
    var customer = createCustomer();
    customer.setEmail(customerEmail);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerEmailLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerEmail = "ab";
    customer.setEmail(customerEmail);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerEmailLengthIsBiggerThan500Characters() throws Exception {
    var customer = createCustomer();
    var customerEmail = StringUtil.generateStringLength(501);
    customer.setEmail(customerEmail);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerDocNumberWasNotFilled(String customerDocNumber)
      throws Exception {
    var customer = createCustomer();
    customer.setDocNumber(customerDocNumber);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678",
      "123456789", "1234567890"})
  void shouldReturnBadRequestWhenCustomerDocNumberLengthIsInvalid(String docNumber)
      throws Exception {
    var customer = createCustomer();
    customer.setDocNumber(docNumber);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerDocNumberLengthIsBiggerThan14Characters()
      throws Exception {
    var customer = createCustomer();
    var docNumber = StringUtil.generateStringLength(15);
    customer.setDocNumber(docNumber);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void shouldReturnBadRequestWhenCustomerDocNumberIsAnInvalidCpf(String number) throws Exception {
    var customer = createAndPersistNewCustomer();
    customer.setDocNumberType(DocNumberType.CPF);
    var cpf = StringUtil.generateStringRepeatCharLength(number, 11);
    customer.setDocNumber(cpf);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void shouldReturnBadRequestWhenCustomerDocNumberIsAnInvalidCnpj(String number) throws Exception {
    var customer = createAndPersistNewCustomer();
    customer.setDocNumberType(DocNumberType.CNPJ);
    var cnpj = StringUtil.generateStringRepeatCharLength(number, 14);
    customer.setDocNumber(cnpj);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerDocNumberAlreadyExists() throws Exception {
    createAndPersistNewCustomer();
    var customer = createAndPersistOtherCustomer();
    var customerAux = CustomerTestData.createCustomer();
    customerAux.setId(customer.getId());
    customerAux.setDocNumber(DEFAULT_CUSTOMER_CPF_DOC_NUMBER);
    var customerInputDto = JsonUtil.toJson(customerAux);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customerAux.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerStreetWasNotFilled(String customerStreet)
      throws Exception {
    var customer = createCustomer();
    customer.setStreet(customerStreet);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerStreetLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerStreet = StringUtil.generateStringLength(2);
    customer.setStreet(customerStreet);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerStreetLengthIsBiggerThan255Characters() throws Exception {
    var customer = createCustomer();
    var customerStreet = StringUtil.generateStringLength(256);
    customer.setStreet(customerStreet);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerAddressNumberWasNotFilled(String customerAddressNumber)
      throws Exception {
    var customer = createCustomer();
    customer.setNumber(customerAddressNumber);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerAddressNumberLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerAddressNumber = StringUtil.generateStringLength(2);
    customer.setNumber(customerAddressNumber);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerAddressNumberLengthIsBiggerThan100Characters()
      throws Exception {
    var customer = createCustomer();
    var customerAddressNumber = StringUtil.generateStringLength(101);
    customer.setNumber(customerAddressNumber);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerNeighborhoodWasNotFilled(String customerNeighborhood)
      throws Exception {
    var customer = createCustomer();
    customer.setNeighborhood(customerNeighborhood);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerNeighborhoodLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerNeighborhood = StringUtil.generateStringLength(2);
    customer.setNeighborhood(customerNeighborhood);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerNeighborhoodLengthIsBiggerThan100Characters()
      throws Exception {
    var customer = createCustomer();
    var customerNeighborhood = StringUtil.generateStringLength(101);
    customer.setNeighborhood(customerNeighborhood);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerCityWasNotFilled(String customerCity) throws Exception {
    var customer = createCustomer();
    customer.setCity(customerCity);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerCityLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var city = StringUtil.generateStringLength(2);
    customer.setCity(city);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerCityLengthIsBiggerThan100Characters() throws Exception {
    var customer = createCustomer();
    var city = StringUtil.generateStringLength(101);
    customer.setCity(city);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerStateWasNotFilled(String customerState) throws Exception {
    var customer = createCustomer();
    customer.setState(customerState);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerStateLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerState = "S";
    customer.setState(customerState);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerStateLengthIsBiggerThan2Characters() throws Exception {
    var customer = createCustomer();
    var customerState = "SAO";
    customer.setState(customerState);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerCountryWasNotFilled(String customerCountry)
      throws Exception {
    var customer = createCustomer();
    customer.setCountry(customerCountry);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerCountryLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerCountry = "AA";
    customer.setCountry(customerCountry);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerCountryLengthIsBiggerThan100Characters() throws Exception {
    var customer = createCustomer();
    var customerCountry = StringUtil.generateStringLength(101);
    customer.setCountry(customerCountry);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCustomerPostalCodeWasNotFilled(String customerPostalCode)
      throws Exception {
    var customer = createCustomer();
    customer.setPostalCode(customerPostalCode);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerPostalCodeLengthIsInvalid() throws Exception {
    var customer = createCustomer();
    var customerPostalCode = "1";
    customer.setPostalCode(customerPostalCode);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerPostalCodeLengthIsBiggerThan8Characters()
      throws Exception {
    var customer = createCustomer();
    var customerPostalCode = StringUtil.generateStringLength(9);
    customer.setPostalCode(customerPostalCode);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCustomerPostalCodeHasAnAlphaCharacter()
      throws Exception {
    var customer = createCustomer();
    var customerPostalCode = "A123456B";
    customer.setPostalCode(customerPostalCode);
    var customerInputDto = JsonUtil.toJson(customer);

    var request = MockMvcRequestBuilders.put(URL_CUSTOMERS, customer.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerInputDto);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
