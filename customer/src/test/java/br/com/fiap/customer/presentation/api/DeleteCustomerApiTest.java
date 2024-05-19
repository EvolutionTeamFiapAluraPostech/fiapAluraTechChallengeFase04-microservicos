package br.com.fiap.customer.presentation.api;

import static br.com.fiap.customer.shared.testdata.CustomerTestData.createNewCustomer;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.customer.domain.entity.Customer;
import br.com.fiap.customer.shared.annotation.DatabaseTest;
import br.com.fiap.customer.shared.annotation.IntegrationTest;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class DeleteCustomerApiTest {

  private static final String URL_CUSTOMERS = "/customers/{id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  DeleteCustomerApiTest(MockMvc mockMvc, EntityManager entityManager) {
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

  @Test
  void shouldDeleteCustomer() throws Exception {
    var customer = createAndPersistNewCustomer();

    var request = MockMvcRequestBuilders.delete(URL_CUSTOMERS, customer.getId());
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNoContent());

    var customerFound = getCustomerFoundById(customer.getId().toString());
    assertThat(customerFound).isNotNull();
    assertThat(customerFound.getId()).isNotNull().isEqualTo(customer.getId());
    assertThat(customerFound.getDeleted()).isTrue();
  }

  @ParameterizedTest
  @ValueSource(strings = {"1Ab"})
  void shouldReturnBadRequestWhenCustomerIdIsInvalid(String customerId) throws Exception {
    var request = MockMvcRequestBuilders.delete(URL_CUSTOMERS, customerId);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenCustomerIdWasNotFound() throws Exception {
    var request = MockMvcRequestBuilders.delete(URL_CUSTOMERS, UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
