package br.com.fiap.logistics.application.usecase;

import static br.com.fiap.logistics.domain.enums.LogisticEnum.ANALISE;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createLogistic;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createNewLogisticDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.service.LogisticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateLogisticsOrderUseCaseTest {

  @Mock
  private LogisticService logisticService;
  @InjectMocks
  private CreateLogisticsOrderUseCase createLogisticsOrderUseCase;

  @Test
  void shouldCreateLogistics() {
    var logisticDto = createNewLogisticDto();
    var logisticSaved = createLogistic();
    logisticSaved.setStatus(ANALISE);
    when(logisticService.save(any(Logistic.class))).thenReturn(logisticSaved);

    var logisticCreated = createLogisticsOrderUseCase.execute(logisticDto);

    assertThat(logisticCreated).isNotNull();
    assertThat(logisticCreated.getId()).isNotNull();
  }
}
