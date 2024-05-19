package br.com.fiap.customer.application.validator;

import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER;
import static br.com.fiap.customer.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF_DOC_NUMBER;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customer.domain.enums.DocNumberType;
import br.com.fiap.customer.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocNumberRequiredValidatorTest {

  @Spy
  private DocNumberRequiredValidator docNumberRequiredValidator;

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenEnterAnInvalidDocNumber(String value) {
    assertThatThrownBy(() -> docNumberRequiredValidator.validate(value, null))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shoudValidateWhenEnterAValidCpfDocNumber() {
    assertThatCode(
        () -> docNumberRequiredValidator.validate(DEFAULT_CUSTOMER_CPF_DOC_NUMBER, DocNumberType.CPF))
        .doesNotThrowAnyException();
  }

  @Test
  void shoudValidateWhenEnterAValidCnpjDocNumber() {
    assertThatCode(
        () -> docNumberRequiredValidator.validate(DEFAULT_CUSTOMER_CNPJ_DOC_NUMBER, DocNumberType.CNPJ))
        .doesNotThrowAnyException();
  }
}
