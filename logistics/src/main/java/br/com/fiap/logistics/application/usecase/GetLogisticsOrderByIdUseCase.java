package br.com.fiap.logistics.application.usecase;

import br.com.fiap.logistics.application.validator.UuidValidator;
import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.service.LogisticService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetLogisticsOrderByIdUseCase {

  private final LogisticService logisticService;
  private final UuidValidator uuidValidator;

  public GetLogisticsOrderByIdUseCase(LogisticService logisticService, UuidValidator uuidValidator) {
    this.logisticService = logisticService;
    this.uuidValidator = uuidValidator;
  }

  public Logistic execute(String id) {
    uuidValidator.validate(id);
    return logisticService.findByIdRequired(UUID.fromString(id));
  }
}
