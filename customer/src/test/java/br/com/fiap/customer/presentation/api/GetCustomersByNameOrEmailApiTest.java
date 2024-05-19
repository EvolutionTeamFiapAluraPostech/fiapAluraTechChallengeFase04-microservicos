package br.com.fiap.customer.presentation.api;

import static br.com.fiap.customer.shared.testdata.CustomerTestData.createNewCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.presentation.api.dto.CustomerContent;
import br.com.fiap.customer.presentation.api.dto.CustomerOutputDto;
import br.com.fiap.customer.shared.annotation.DatabaseTest;
import br.com.fiap.customer.shared.annotation.IntegrationTest;
import br.com.fiap.customer.shared.api.JsonUtil;
import br.com.fiap.customer.shared.api.PageUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetCustomersByNameOrEmailApiTest {

  private static final String URL_CUSTOMERS = "/customers/name-email";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetCustomersByNameOrEmailApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private Customer createAndPersistNewCustomer() {
    var customer = createNewCustomer();
    return entityManager.merge(customer);
  }

  @Test
  void shouldReturnOkWhenFindCustomerByName() throws Exception {
    var customer = createAndPersistNewCustomer();
    var customerPage = PageUtil.generatePageOfCustomer(customer);
    var customerOutputDtoExpected = CustomerOutputDto.toPage(customerPage);

    var request = get(URL_CUSTOMERS)
        .param("name", customer.getName())
        .param("email", "");
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var customerFound = JsonUtil.fromJson(contentAsString, CustomerContent.class);
    assertThat(customerFound.getContent().get(0).id()).isEqualTo(
        customerOutputDtoExpected.getContent().get(0).id());
  }

  @Test
  void shouldReturnOkWhenFindCustomerByEmail() throws Exception {
    var customer = createAndPersistNewCustomer();
    var customerPage = PageUtil.generatePageOfCustomer(customer);
    var customerOutputDtoExpected = CustomerOutputDto.toPage(customerPage);

    var request = get(URL_CUSTOMERS)
        .param("name", "")
        .param("email", customer.getEmail());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var customerFound = JsonUtil.fromJson(contentAsString, CustomerContent.class);
    assertThat(customerFound.getContent().get(0).id()).isEqualTo(
        customerOutputDtoExpected.getContent().get(0).id());
  }

  @Test
  void shouldReturnOkWhenFindCustomerByNameAndEmail() throws Exception {
    var customer = createAndPersistNewCustomer();
    var customerPage = PageUtil.generatePageOfCustomer(customer);
    var customerOutputDtoExpected = CustomerOutputDto.toPage(customerPage);

    var request = get(URL_CUSTOMERS)
        .param("name", customer.getName())
        .param("email", customer.getEmail());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var customerFound = JsonUtil.fromJson(contentAsString, CustomerContent.class);
    assertThat(customerFound.getContent().get(0).id()).isEqualTo(
        customerOutputDtoExpected.getContent().get(0).id());
  }

  @Test
  void shouldReturnOkWhenFindAllCustomersByEmptyNameAndEmptyEmail() throws Exception {
    var request = get(URL_CUSTOMERS)
        .param("name", "")
        .param("email", "");
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var customerFound = JsonUtil.fromJson(contentAsString, CustomerContent.class);
    assertThat(customerFound.getContent()).hasSize(1);
  }

  @Test
  void shouldReturnOkWhenFindAllCustomersButNothingWasFound() throws Exception {
    var request = get(URL_CUSTOMERS)
        .param("name", "Agent Smith")
        .param("email", "agent.smith@matrix.com");
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
