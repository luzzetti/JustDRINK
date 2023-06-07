package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Customer {

  private final CustomerId id;
  private CustomerName customerName;

  // TODO: da fare
  private PhoneNumber phoneNumber;

  @Builder.Default private Set<Address> addressBook = new HashSet<>();

  public void addAddress(Address address) {
    this.addressBook.add(address);
  }

  // TODO: implementare un test !!
  public void removeAddress(AddressId addressId) {
    this.addressBook.removeIf(address -> Objects.equals(address.getId(), addressId));
  }

  public void changeCustomerName(String firstName, String lastName) {
    this.customerName = CustomerName.of(firstName, lastName);
  }

  public void changeCustomerName(CustomerName customerName) {
    this.customerName = customerName;
  }

  public static CustomerBuilder builder() {
    return new CustomBuilder();
  }

  // Validations

  private static class CustomBuilder extends CustomerBuilder {

    /* Adding validations as part of build() method. */
    public Customer build() {
      // TODO controllare id!!

      Objects.requireNonNull(super.customerName, "Customer name cannot be null");

      return super.build();
    }
  }
}
