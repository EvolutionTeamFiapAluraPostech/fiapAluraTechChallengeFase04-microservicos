package br.com.fiap.customer.presentation.api;

import static br.com.fiap.customer.shared.testdata.CustomerTestData.createNewCustomer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.shared.annotation.DatabaseTest;
import br.com.fiap.customer.shared.annotation.IntegrationTest;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
@DatabaseTest
class GetCustomerByIdApiTest {

  private static final String URL_CUSTOMERS = "/customers/{id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetCustomerByIdApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private Customer createAndPersistNewCustomer() {
    var customer = createNewCustomer();
    return entityManager.merge(customer);
  }

  @Test
  void shouldGetCustomerById() throws Exception {
    var customer = createAndPersistNewCustomer();

    var request = MockMvcRequestBuilders.get(URL_CUSTOMERS, customer.getId());
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", equalTo(customer.getId().toString())))
        .andExpect(jsonPath("$.name", equalTo(customer.getName())))
        .andExpect(jsonPath("$.docNumber", equalTo(customer.getDocNumber())));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a", "1a#"})
  void shouldReturnBadRequestWhenCustomerIdIsInvalid(String id) throws Exception {
    var request = MockMvcRequestBuilders.get(URL_CUSTOMERS, id);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldReturnNotFoundWhenCustomerIdDoesNotExist() throws Exception  {
    var request = MockMvcRequestBuilders.get(URL_CUSTOMERS, UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
