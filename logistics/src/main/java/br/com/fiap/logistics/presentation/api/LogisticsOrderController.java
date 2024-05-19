package br.com.fiap.logistics.presentation.api;

import br.com.fiap.logistics.application.usecase.ConfirmLogisticOrderDeliveryUseCase;
import br.com.fiap.logistics.application.usecase.CreateLogisticsOrderUseCase;
import br.com.fiap.logistics.application.usecase.GetLogisticsOrderByIdUseCase;
import br.com.fiap.logistics.application.usecase.OrderIsReadyToDeliverUseCase;
import br.com.fiap.logistics.presentation.api.dto.LogisticOrderDto;
import br.com.fiap.logistics.presentation.api.dto.LogisticsOrderInputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logistics")
public class LogisticsOrderController implements LogisticsOrderApi {

  private final CreateLogisticsOrderUseCase createLogisticsOrderUseCase;
  private final GetLogisticsOrderByIdUseCase getLogisticsOrderByIdUseCase;
  private final OrderIsReadyToDeliverUseCase orderIsReadyToDeliverUseCase;
  private final ConfirmLogisticOrderDeliveryUseCase confirmLogisticOrderDeliveryUseCase;

  public LogisticsOrderController(CreateLogisticsOrderUseCase createLogisticsOrderUseCase,
      GetLogisticsOrderByIdUseCase getLogisticsOrderByIdUseCase,
      OrderIsReadyToDeliverUseCase orderIsReadyToDeliverUseCase,
      ConfirmLogisticOrderDeliveryUseCase confirmLogisticOrderDeliveryUseCase) {
    this.createLogisticsOrderUseCase = createLogisticsOrderUseCase;
    this.getLogisticsOrderByIdUseCase = getLogisticsOrderByIdUseCase;
    this.orderIsReadyToDeliverUseCase = orderIsReadyToDeliverUseCase;
    this.confirmLogisticOrderDeliveryUseCase = confirmLogisticOrderDeliveryUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public LogisticOrderDto postOrderLogistics(
      @RequestBody @Valid LogisticsOrderInputDto logisticsOrderInputDto) {
    var logisticsSaved = createLogisticsOrderUseCase.execute(logisticsOrderInputDto);
    return LogisticOrderDto.from(logisticsSaved);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public LogisticOrderDto getLogisticsOrderById(@PathVariable String id) {
    var logistic = getLogisticsOrderByIdUseCase.execute(id);
    return LogisticOrderDto.from(logistic);
  }

  @PatchMapping("/{id}/order-is-ready-to-deliver")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void patchLogisticsOrderIsReadyToDelivery(String id) {
    orderIsReadyToDeliverUseCase.execute(id);
  }

  @PatchMapping("/{id}/delivery-confirmation")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Override
  public void patchLogisticsOrderDeliveryConfirmation(@PathVariable String id) {
    confirmLogisticOrderDeliveryUseCase.execute(id);
  }
}
