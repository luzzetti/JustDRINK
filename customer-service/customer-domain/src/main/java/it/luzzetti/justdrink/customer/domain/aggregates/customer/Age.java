package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import java.time.LocalDate;
import java.time.Period;
import lombok.Value;

@Value
public class Age {
  int age;

  private Age(int age) {
    if (age < 0 || age > 150) {
      throw new IllegalArgumentException("change me");
    }
    this.age = age;
  }

  public static Age from(BirthDate birthDate) {
    int age = Period.between(birthDate.getBirthday(), LocalDate.now()).getYears();
    return new Age(age);
  }
}
