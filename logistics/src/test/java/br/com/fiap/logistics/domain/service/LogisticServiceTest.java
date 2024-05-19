package br.com.fiap.logistics.domain.service;

import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_NOT_FOUND_BY_ID_MESSAGE;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createLogistic;
import static br.com.fiap.logistics.shared.testdata.LogisticsTestData.createNewLogistic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.exception.NoResultException;
import br.com.fiap.logistics.domain.fields.LogisticFields;
import br.com.fiap.logistics.infrastructure.repository.LogisticRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class LogisticServiceTest {

  @Mock
  private LogisticRepository logisticRepository;
  @InjectMocks
  private LogisticService logisticService;

  @Test
  void shouldSaveLogistics() {
    var newLogistic = createNewLogistic();
    var logistic = createLogistic();
    when(logisticRepository.save(any(Logistic.class))).thenReturn(logistic);

    var logisticsSaved = logisticService.save(newLogistic);

    assertThat(logisticsSaved).isNotNull();
    assertThat(logisticsSaved.getId()).isNotNull().isEqualTo(logistic.getId());
  }

  @Test
  void shouldFindLogisticById() {
    var logistics = createLogistic();
    when(logisticRepository.findById(logistics.getId()))
        .thenReturn(Optional.of(logistics));

    var logisticsFound = logisticService.findByIdRequired(logistics.getId());

    assertThat(logisticsFound).isNotNull();
    assertThat(logisticsFound.getId()).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenLogisticWasNotFoundById() {
    var logistics = createLogistic();
    when(logisticRepository.findById(logistics.getId()))
        .thenThrow(new NoResultException(new FieldError(this.getClass().getSimpleName(),
            LogisticFields.LOGISTICS_ID_FIELD,
            LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(logistics.getId()))));

    assertThatThrownBy(() -> logisticService.findByIdRequired(logistics.getId()))
        .isInstanceOf(NoResultException.class)
        .hasMessage(LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(logistics.getId()));
  }
}