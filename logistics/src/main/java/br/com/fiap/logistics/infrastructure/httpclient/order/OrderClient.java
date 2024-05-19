package br.com.fiap.logistics.infrastructure.httpclient.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "order", url = "${base.url.http-order}")
public interface OrderClient {

  @PutMapping("/orders/{id}/delivery-confirmation")
  void putOrderDeliveryConfirmation(@PathVariable String id);
}
