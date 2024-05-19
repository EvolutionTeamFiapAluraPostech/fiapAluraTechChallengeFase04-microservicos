package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.domain.enums.DocNumberType.CNPJ;
import static br.com.fiap.customer.domain.enums.DocNumberType.CPF;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF_DOC_NUMBER;
import static org.assertj.core.api.Assertions.assertThatCode;

import br.com.fiap.customer.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocNumberTypeValidatorTest {

  @Spy
  private CPFValidator cpfValidator;
  @Spy
  private CNPJValidator cnpjValidator;
  @InjectMocks
  private DocNumberTypeValidator docNumberTypeValidator;

  @Test
  void shouldValidateWhenDocNumberTypeIsCpf() {
    assertThatCode(() -> docNumberTypeValidator.validate(DEFAULT_CUSTOMER_CPF_DOC_NUMBER, CPF))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenDocNumberTypeCpfIsInvalid() {
    assertThatCode(() -> docNumberTypeValidator.validate(DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER, CPF))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldValidateWhenDocNumberTypeIsCnpj() {
    assertThatCode(() -> docNumberTypeValidator.validate(DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER, CNPJ))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenDocNumberTypeCnpjIsInvalid() {
    assertThatCode(() -> docNumberTypeValidator.validate(DEFAULT_CUSTOMER_CPF_DOC_NUMBER, CNPJ))
        .isInstanceOf(ValidatorException.class);
  }
}