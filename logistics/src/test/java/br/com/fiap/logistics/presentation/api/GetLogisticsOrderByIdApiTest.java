package br.com.fiap.logistics.presentation.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.shared.annotation.DatabaseTest;
import br.com.fiap.logistics.shared.annotation.IntegrationTest;
import br.com.fiap.logistics.shared.testdata.LogisticsTestData;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetLogisticsOrderByIdApiTest {

  private static final String URL_LOGISTICS = "/logistics/{id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetLogisticsOrderByIdApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private Logistic createAndPersist() {
    var logistic = LogisticsTestData.createNewLogistic();
    return entityManager.merge(logistic);
  }

  @Test
  void shouldReturnOkWhenLogisticWasFoundById() throws Exception {
    var logistic = createAndPersist();

    var request = get(URL_LOGISTICS, logistic.getId());
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", equalTo(logistic.getId().toString())));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a", "1a#"})
  void shouldReturnBadRequestWhenOrderIdIsInvalid(String orderId) throws Exception {
    var request = get(URL_LOGISTICS, orderId);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldReturnNotFoundWhenOrderIdDoesNotExist() throws Exception {
    var request = get(URL_LOGISTICS, UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(APPLICATION_JSON));
  }
}
