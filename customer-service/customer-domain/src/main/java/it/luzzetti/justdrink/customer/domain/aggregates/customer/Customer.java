package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Builder
public class Customer {

  private CustomerId id;
  @Getter private String name;
  @Builder.Default private Set<Address> addressBook = new HashSet<>();

  public void addAddressToAddressBook(Address address) {
    this.addressBook.add(address);
  }
  public void removeAddressToAddressBook(Address address) {
    this.addressBook.remove(address);
  }
}
