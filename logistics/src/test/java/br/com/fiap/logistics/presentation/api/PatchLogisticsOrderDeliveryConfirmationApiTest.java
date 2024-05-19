package br.com.fiap.logistics.presentation.api;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;
import static br.com.fiap.logistics.domain.enums.LogisticEnum.EM_ROTA_DE_ENTREGA;
import static br.com.fiap.logistics.domain.enums.LogisticEnum.ENTREGUE;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.shared.annotation.DatabaseTest;
import br.com.fiap.logistics.shared.annotation.IntegrationTest;
import br.com.fiap.logistics.shared.api.JsonUtil;
import br.com.fiap.logistics.shared.testdata.LogisticsTestData;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
@WireMockTest(httpPort = 7070)
class PatchLogisticsOrderDeliveryConfirmationApiTest {

  private static final String URL_LOGISTICS_DELIVERY_CONFIRMATION = "/logistics/{id}/delivery-confirmation";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PatchLogisticsOrderDeliveryConfirmationApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private Logistic createAndPersistLogistics() {
    var logistic = LogisticsTestData.createNewLogistic();
    return entityManager.merge(logistic);
  }

  private Logistic getLogisticById(UUID id) {
    return entityManager.find(Logistic.class, id);
  }

  @Test
  void shouldReturnNoContentWhenConfirmDeliveryOrder() throws Exception {
    var logistic = createAndPersistLogistics();
    logistic.setStatus(EM_ROTA_DE_ENTREGA);
    var logisticInputDto = JsonUtil.toJson(logistic);
    stubFor(WireMock.put("/orders/" + logistic.getOrderId() + "/delivery-confirmation")
        .willReturn(WireMock.noContent()));

    var request = patch(URL_LOGISTICS_DELIVERY_CONFIRMATION, logistic.getId())
        .contentType(APPLICATION_JSON)
        .content(logisticInputDto);
    mockMvc.perform(request)
        .andExpect(status().isNoContent());

    var logisticFound = getLogisticById(logistic.getId());
    assertThat(logisticFound).isNotNull();
    assertThat(logisticFound.getId()).isNotNull().isEqualTo(logistic.getId());
    assertThat(logisticFound.getStatus()).isNotNull().isEqualTo(ENTREGUE);
  }

  @Test
  void shouldReturnBadRequestWhenOrderIdIsInvalid() throws Exception {
    var logistic = LogisticsTestData.createLogistic();
    var logisticInputDto = JsonUtil.toJson(logistic);
    var logisticId = "1aB";
    var request = patch(URL_LOGISTICS_DELIVERY_CONFIRMATION, logisticId)
        .contentType(APPLICATION_JSON)
        .content(logisticInputDto);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenLogisticWasNotFound() throws Exception {
    var logistic = LogisticsTestData.createLogistic();
    var logisticInputDto = JsonUtil.toJson(logistic);
    var logisticId = UUID.randomUUID();
    var request = patch(URL_LOGISTICS_DELIVERY_CONFIRMATION, logisticId)
        .contentType(APPLICATION_JSON)
        .content(logisticInputDto);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenLogisticIsAlreadyDelivered() throws Exception {
    var logistic = createAndPersistLogistics();
    logistic.setStatus(ENTREGUE);
    var logisticDeliveryConfirmation = entityManager.merge(logistic);
    var logisticInputDto = JsonUtil.toJson(logisticDeliveryConfirmation);

    var request = patch(URL_LOGISTICS_DELIVERY_CONFIRMATION, logisticDeliveryConfirmation.getId())
        .contentType(APPLICATION_JSON)
        .content(logisticInputDto);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenLogisticIsInAnaliseAndItCannotBeDeliver() throws Exception {
    var logistic = createAndPersistLogistics();
    logistic.setStatus(ANALISE);
    var logisticDeliverConfirmation = entityManager.merge(logistic);
    var logisticInputDto = JsonUtil.toJson(logisticDeliverConfirmation);

    var request = patch(URL_LOGISTICS_DELIVERY_CONFIRMATION, logisticDeliverConfirmation.getId())
        .contentType(APPLICATION_JSON)
        .content(logisticInputDto);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }
}
