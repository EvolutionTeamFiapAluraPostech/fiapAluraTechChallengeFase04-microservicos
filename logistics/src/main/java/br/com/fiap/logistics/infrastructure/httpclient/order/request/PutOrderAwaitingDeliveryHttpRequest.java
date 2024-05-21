package br.com.fiap.logistics.infrastructure.httpclient.order.request;

import br.com.fiap.logistics.infrastructure.httpclient.order.OrderClient;
import org.springframework.stereotype.Service;

@Service
public class PutOrderAwaitingDeliveryHttpRequest {

  private final OrderClient orderClient;

  public PutOrderAwaitingDeliveryHttpRequest(OrderClient orderClient) {
    this.orderClient = orderClient;
  }

  public void request(String id) {
    orderClient.putOrderAwaitingDelivery(id);
  }
}
