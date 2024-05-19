package br.com.fiap.logistics.presentation.api;

import static br.com.fiap.logistics.shared.IsUUID.isUUID;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createNewLogistic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.shared.annotation.DatabaseTest;
import br.com.fiap.logistics.shared.annotation.IntegrationTest;
import br.com.fiap.logistics.shared.api.JsonUtil;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PostOrderLogisticApiTest {

  private static final String URL_LOGISTICS = "/logistics";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostOrderLogisticApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private Logistic findLogisticById(UUID id) {
    return entityManager.find(Logistic.class, id);
  }

  @Test
  void shouldReturnCreatedWhenNewLogisticWasSaved() throws Exception {
    var logistics = createNewLogistic();
    var logisticsInputDto = JsonUtil.toJson(logistics);

    var request = post(URL_LOGISTICS)
        .contentType(APPLICATION_JSON)
        .content(logisticsInputDto);
    var mockMvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mockMvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var logisticsFound = findLogisticById(UUID.fromString(id));
    assertThat(logisticsFound).isNotNull();
    assertThat(logisticsFound.getId()).isNotNull().isEqualTo(UUID.fromString(id));
    assertThat(logisticsFound.getCompanyId()).isNotNull().isEqualTo(logistics.getCompanyId());
    assertThat(logisticsFound.getCustomerId()).isNotNull().isEqualTo(logistics.getCustomerId());
    assertThat(logisticsFound.getLogisticsItems()).isNotEmpty().hasSize(1);
    assertThat(logisticsFound.getLogisticsItems().get(0)).isNotNull();
    assertThat(logisticsFound.getLogisticsItems().get(0).getId()).isNotNull();
  }

  @Test
  void shouldReturnBadRequestWhenLogisticsHaveInvalidAttributes() throws Exception {
    var logisticsInputDto = """
        { "companyId":"dcd3398e-4988-4fba-b8c0-a649ae1ff677",
          "orderId":"",
          "customerId":"",
          "logisticsItems":
          [
            {
              "orderItemId":"d1162a37-f36e-44f9-8d02-c1d2b650f073",
              "productId":"d1162a37-f36e-44f9-8d02-c1d2b650f073",
              "quantity":10,
              "price":315
            }
          ]
        }
        """;

    var request = post(URL_LOGISTICS)
        .contentType(APPLICATION_JSON)
        .content(logisticsInputDto);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenLogisticsItemListIsEmpty() throws Exception {
    var logisticsInputDto = """
        { "companyId":"dcd3398e-4988-4fba-b8c0-a649ae1ff677",
          "orderId":"d1162a37-f36e-44f9-8d02-c1d2b650f073",
          "customerId":"d1162a37-f36e-44f9-8d02-c1d2b650f073",
          "logisticsItems": []
        }
        """;

    var request = post(URL_LOGISTICS)
        .contentType(APPLICATION_JSON)
        .content(logisticsInputDto);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenLogisticsItemHaveInvalidAttributes() throws Exception {
    var logisticsInputDto = """
        { "companyId":"dcd3398e-4988-4fba-b8c0-a649ae1ff677",
          "orderId":"d1162a37-f36e-44f9-8d02-c1d2b650f073",
          "customerId":"d1162a37-f36e-44f9-8d02-c1d2b650f073",
          "logisticsItems":
          [
            {
              "orderItemId":"",
              "productId":"",
              "quantity":10,
              "price":315
            }
          ]
        }
        """;

    var request = post(URL_LOGISTICS)
        .contentType(APPLICATION_JSON)
        .content(logisticsInputDto);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }
}
