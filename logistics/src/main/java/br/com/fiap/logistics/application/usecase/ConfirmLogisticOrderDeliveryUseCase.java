package br.com.fiap.logistics.application.usecase;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ENTREGUE;

import br.com.fiap.logistics.application.validator.LogisticOrderAlreadyDeliveredValidator;
import br.com.fiap.logistics.application.validator.LogisticOrderIsNotReadyToDeliverValidator;
import br.com.fiap.logistics.application.validator.UuidValidator;
import br.com.fiap.logistics.domain.service.LogisticService;
import br.com.fiap.logistics.infrastructure.httpclient.order.request.PutOrderDeliveryConfirmationHttpRequest;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmLogisticOrderDeliveryUseCase {

  private final LogisticService logisticService;
  private final UuidValidator uuidValidator;
  private final LogisticOrderAlreadyDeliveredValidator logisticOrderAlreadyDeliveredValidator;
  private final LogisticOrderIsNotReadyToDeliverValidator logisticOrderIsNotReadyToDeliverValidator;
  private final PutOrderDeliveryConfirmationHttpRequest putOrderDeliveryConfirmationHttpRequest;

  public ConfirmLogisticOrderDeliveryUseCase(LogisticService logisticService,
      UuidValidator uuidValidator,
      LogisticOrderAlreadyDeliveredValidator logisticOrderAlreadyDeliveredValidator,
      LogisticOrderIsNotReadyToDeliverValidator logisticOrderIsNotReadyToDeliverValidator,
      PutOrderDeliveryConfirmationHttpRequest putOrderDeliveryConfirmationHttpRequest) {
    this.logisticService = logisticService;
    this.uuidValidator = uuidValidator;
    this.logisticOrderAlreadyDeliveredValidator = logisticOrderAlreadyDeliveredValidator;
    this.logisticOrderIsNotReadyToDeliverValidator = logisticOrderIsNotReadyToDeliverValidator;
    this.putOrderDeliveryConfirmationHttpRequest = putOrderDeliveryConfirmationHttpRequest;
  }

  @Transactional
  public void execute(String id) {
    uuidValidator.validate(id);
    var logistic = logisticService.findByIdRequired(UUID.fromString(id));
    logisticOrderAlreadyDeliveredValidator.validate(logistic);
    logisticOrderIsNotReadyToDeliverValidator.validate(logistic);
    logistic.setStatus(ENTREGUE);
    logisticService.save(logistic);
    putOrderDeliveryConfirmationHttpRequest.request(logistic.getOrderId().toString());
  }
}
