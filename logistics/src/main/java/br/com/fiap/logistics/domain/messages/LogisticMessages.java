package br.com.fiap.logistics.domain.messages;

public final class LogisticMessages {

  public static final String LOGISTICS_NOT_FOUND_BY_ID_MESSAGE = "Logistics not found by ID %s";
  public static final String LOGISTICS_WITH_INVALID_UUID_MESSAGE = "Logistics with invalid ID %s";
  public static final String LOGISTIC_ORDER_WAS_ALREADY_DELIVERED_MESSAGE = "Logistic order was already delivered ID %s";
  public static final String LOGISTIC_ORDER_IS_NOT_READY_TO_DELIVER_MESSAGE = "Logistic is not ready to deliver because its status is %s. ID %s";
  public static final String LOGISTIC_ORDER_IS_ALREADY_IN_ROUTE_TO_DELIVER_MESSAGE = "Logistic is already in route to deliver. ID %s";

  private LogisticMessages() {
  }
}
