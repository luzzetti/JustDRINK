package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import lombok.Value;

@Value
public class PhoneNumber {
  String value;

  private PhoneNumber(String value) {
    this.value = value;
  }

  public static PhoneNumber from(String value) {
    return new PhoneNumber(value);
  }
}
