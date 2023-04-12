package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import static org.junit.jupiter.api.Assertions.assertThrows;

import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

  @ParameterizedTest
  @ValueSource(strings = {"    ", " nomeNonValido ", "altroN0meC0nNumer1", "nomeConsymbol/$%"})
  @NullSource
  void whenCreatingProductWithInvalidName_thanExceptionIsThrown(String anInvalidName) {
    assertThrows(
        ValidationException.class, () -> Product.newProduct(anInvalidName, BigDecimal.TEN));
  }
}
