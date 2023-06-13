package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BirthDateTest {
  @Test
  @DisplayName("test")
  void whenTheBirthDateIsAfterNow_thenThrowsApplicationException() {
    LocalDate date = LocalDate.of(2100, 12, 12);
    assertThrows(IllegalArgumentException.class, () -> BirthDate.from(date));
  }
}
