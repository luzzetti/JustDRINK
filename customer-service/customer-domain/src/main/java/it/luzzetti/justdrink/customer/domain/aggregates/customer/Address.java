package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;
import lombok.Builder;

@Builder
public class Address {

  private AddressId id;
  private String name;
  private String city;
  private String postalCode;
  private String street;
}
