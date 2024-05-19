package br.com.fiap.customer.domain.messages;

public final class CustomerMessages {

  public static final String ENTER_DOCUMENT_NUMBER_MESSAGE = "Enter the document number.";
  public static final String DOCUMENT_NUMBER_ALREADY_EXISTS_MESSAGE = "Document number already exists. %s";
  public static final String DOCUMENT_NUMBER_ALREADY_EXISTS_IN_OTHER_CUSTOMER_MESSAGE = "Document number already exists in other customer. %s";
  public static final String UUID_INVALID_MESSAGE = "Invalid UUID. %s";
  public static final String CPF_INVALID_MESSAGE = "Invalid CPF. %s";
  public static final String CNPJ_INVALID_MESSAGE = "Invalid CNPJ. %s";
  public static final String CUSTOMER_NOT_FOUND_WITH_ID_MESSAGE = "Customer not found with ID. %s";

  private CustomerMessages() {
  }
}
