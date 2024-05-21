package br.com.fiap.logistics.domain.service;

import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTICS_ID_FIELD;
import static br.com.fiap.logistics.domain.fields.LogisticFields.LOGISTICS_ORDER_ID_FIELD;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_NOT_FOUND_BY_ID_MESSAGE;
import static br.com.fiap.logistics.domain.messages.LogisticMessages.LOGISTICS_NOT_FOUND_BY_ORDER_ID_MESSAGE;

import br.com.fiap.logistics.domain.entity.Logistic;
import br.com.fiap.logistics.domain.exception.NoResultException;
import br.com.fiap.logistics.infrastructure.repository.LogisticRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class LogisticService {

  private final LogisticRepository logisticRepository;

  public LogisticService(LogisticRepository logisticRepository) {
    this.logisticRepository = logisticRepository;
  }

  public Logistic save(Logistic logistics) {
    return logisticRepository.save(logistics);
  }

  public Logistic findByIdRequired(UUID id) {
    return logisticRepository.findById(id).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(),
            LOGISTICS_ID_FIELD, LOGISTICS_NOT_FOUND_BY_ID_MESSAGE.formatted(id))));
  }

  public Logistic findLogisticByOrderIdRequired(UUID orderId) {
    return logisticRepository.findLogisticByOrderId(orderId).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(),
            LOGISTICS_ORDER_ID_FIELD, LOGISTICS_NOT_FOUND_BY_ORDER_ID_MESSAGE.formatted(orderId))));
  }
}
