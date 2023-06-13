package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import java.time.LocalDate;
import lombok.Value;

@Value
public class BirthDate {
  LocalDate birthday;

  private BirthDate(LocalDate birthday) {
    if (birthday == null) {
      throw new IllegalArgumentException("change me");
    }
    if (birthday.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("change me");
    }
    this.birthday = birthday;
  }

  public static BirthDate from(LocalDate birthday) {
    return new BirthDate(birthday);
  }
}
