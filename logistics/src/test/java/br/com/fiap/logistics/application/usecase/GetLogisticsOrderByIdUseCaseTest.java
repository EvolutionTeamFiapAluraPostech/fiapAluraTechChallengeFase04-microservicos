package br.com.fiap.logistics.application.usecase;

import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTICS_ID_FIELD;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_NOT_FOUND_BY_ID_MESSAGE;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_WITH_INVALID_UUID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.logistics.application.validator.UuidValidator;
import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.exception.NoResultException;
import br.com.fiap.logistics.domain.exception.ValidatorException;
import br.com.fiap.logistics.domain.service.LogisticService;
import br.com.fiap.logistics.shared.testdata.LogisticsTestData;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class GetLogisticsOrderByIdUseCaseTest {

  @Mock
  private LogisticService logisticService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private GetLogisticsOrderByIdUseCase getLogisticsOrderByIdUseCase;

  @Test
  void shouldGetLogisticsOrderById() {
    Logistic logistic = LogisticsTestData.createLogistic();
    var id = logistic.getId();
    when(logisticService.findByIdRequired(id)).thenReturn(logistic);

    var logisticFound = getLogisticsOrderByIdUseCase.execute(id.toString());

    assertThat(logisticFound).isNotNull();
    assertThat(logisticFound.getId()).isNotNull();
    verify(uuidValidator).validate(id.toString());
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a", "1a#"})
  void shouldThrowExceptionWhenLogisticIdIsInvalid(String id) {
    doThrow(
        new ValidatorException(new FieldError(this.getClass().getSimpleName(), LOGISTICS_ID_FIELD,
            LOGISTICS_WITH_INVALID_UUID_MESSAGE.formatted(id))))
        .when(uuidValidator).validate(id);

    assertThatThrownBy(() -> getLogisticsOrderByIdUseCase.execute(id))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LOGISTICS_WITH_INVALID_UUID_MESSAGE.formatted(id));
    verify(logisticService, never()).findByIdRequired(any(UUID.class));
  }

  @Test
  void shouldThrowExceptionWhenLogisticWasNotFound() {
    var id = UUID.randomUUID();
    when(logisticService.findByIdRequired(id)).thenThrow(
        new NoResultException(new FieldError(this.getClass().getSimpleName(),
            LOGISTICS_ID_FIELD, LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(id))));

    assertThatThrownBy(() -> getLogisticsOrderByIdUseCase.execute(id.toString()))
        .isInstanceOf(NoResultException.class)
        .hasMessage(LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(id));
    verify(uuidValidator).validate(id.toString());
  }
}
