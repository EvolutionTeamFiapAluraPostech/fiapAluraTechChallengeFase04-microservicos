package br.com.fiap.logistics.application.usecase;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.EM_ROTA_DE_ENTREGA;

import br.com.fiap.logistics.application.validator.LogisticOrderAlreadyDeliveredValidator;
import br.com.fiap.logistics.application.validator.LogisticOrderIsInRouteToDeliverValidator;
import br.com.fiap.logistics.application.validator.UuidValidator;
import br.com.fiap.logistics.domain.service.LogisticService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderIsReadyToDeliverUseCase {

  private final LogisticService logisticService;
  private final UuidValidator uuidValidator;
  private final LogisticOrderAlreadyDeliveredValidator logisticOrderAlreadyDeliveredValidator;
  private final LogisticOrderIsInRouteToDeliverValidator logisticOrderIsAlreadyInRouteToDeliverValidator;

  public OrderIsReadyToDeliverUseCase(LogisticService logisticService, UuidValidator uuidValidator,
      LogisticOrderAlreadyDeliveredValidator logisticOrderAlreadyDeliveredValidator,
      LogisticOrderIsInRouteToDeliverValidator logisticOrderIsAlreadyInRouteToDeliverValidator) {
    this.logisticService = logisticService;
    this.uuidValidator = uuidValidator;
    this.logisticOrderAlreadyDeliveredValidator = logisticOrderAlreadyDeliveredValidator;
    this.logisticOrderIsAlreadyInRouteToDeliverValidator = logisticOrderIsAlreadyInRouteToDeliverValidator;
  }

  @Transactional
  public void execute(String id) {
    uuidValidator.validate(id);
    var logistic = logisticService.findByIdRequired(UUID.fromString(id));
    logisticOrderAlreadyDeliveredValidator.validate(logistic);
    logisticOrderIsAlreadyInRouteToDeliverValidator.validate(logistic);
    logistic.setStatus(EM_ROTA_DE_ENTREGA);
    logisticService.save(logistic);
  }
}
