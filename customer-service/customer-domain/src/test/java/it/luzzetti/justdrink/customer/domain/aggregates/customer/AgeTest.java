package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AgeTest {

  @Test
  @DisplayName("Age calculation - age over 150 years")
  void whenTheResultOfAgeCalculationIsOver150_thenThrowsApplicationException() {
    BirthDate birthDate = BirthDate.from(LocalDate.of(1800, 6, 12));
    assertThrows(IllegalArgumentException.class, () -> Age.from(birthDate));
  }
}
