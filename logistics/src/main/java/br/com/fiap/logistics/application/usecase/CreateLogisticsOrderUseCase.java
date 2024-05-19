package br.com.fiap.logistics.application.usecase;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.service.LogisticService;
import br.com.fiap.logistics.presentation.api.dto.LogisticsOrderInputDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateLogisticsOrderUseCase {

  private final LogisticService logisticService;

  public CreateLogisticsOrderUseCase(LogisticService logisticService) {
    this.logisticService = logisticService;
  }

  @Transactional
  public Logistic execute(LogisticsOrderInputDto logisticsOrderInputDto) {
    var logistic = logisticsOrderInputDto.toLogistics();
    logistic.setStatus(ANALISE);
    return logisticService.save(logistic);
  }
}
