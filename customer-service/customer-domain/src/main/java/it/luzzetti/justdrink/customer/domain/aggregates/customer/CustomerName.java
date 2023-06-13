package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import lombok.Getter;

import java.util.Objects;

@Getter
public class CustomerName {
  private final String firstName;
  private final String lastName;

  private CustomerName(String firstName, String lastName) {
    Objects.requireNonNull(firstName, "Not be null");
    Objects.requireNonNull(lastName, "Not be null");
    this.firstName = firstName;
    this.lastName = lastName;
  }

  // ====== STATIC FACTORY METHOD =======

  // generato dai singoli campi ad esempio LocalDateTime
  public static CustomerName of(String firstName, String lastName) {
    return new CustomerName(firstName, lastName);
  }

  public static CustomerName from(String aString) {
    // TODO da testare
    String[] s = aString.split(" ");
    return new CustomerName(s[0], s[1]);
  }

  public static CustomerName newAnonymousCustomer() {
    return new CustomerName("Unknown", "Unknown");
  }
}
