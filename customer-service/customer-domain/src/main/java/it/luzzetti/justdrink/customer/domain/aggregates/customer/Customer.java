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
  private Gender gender;
  private BirthDate birthDate;
  private PhoneNumber phoneNumber;
  @Builder.Default private Set<Address> addressBook = new HashSet<>();

  public void addAddress(Address address) {
    this.addressBook.add(address);
  }

  public void removeAddress(AddressId addressId) {
    this.addressBook.removeIf(address -> Objects.equals(addressId, address.getId()));
  }

  public void changeCustomerName(CustomerName customerName) {
    this.customerName = customerName;
  }

  public void changeGender(Gender gender) {
    this.gender = gender;
  }

  public Age calculateAge() {
    return Age.from(this.birthDate);
  }

  public void changePhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public static CustomerBuilder builder() {
    return new CustomBuilder();
  }

  private static class CustomBuilder extends CustomerBuilder {

    public Customer build() {
      if (super.id == null || super.id.id() == null) {
        throw new IllegalArgumentException("change me");
      }
      return super.build();
    }
  }
}
