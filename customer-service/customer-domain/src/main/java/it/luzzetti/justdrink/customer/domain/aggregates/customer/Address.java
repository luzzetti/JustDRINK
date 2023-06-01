package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import lombok.Builder;
import lombok.Getter;

// tra due aggregati parli tramite id
// tutte le operazioni le fai sulla root dell'aggregato

@Builder
@Getter
public class Address {

  private AddressId id;
  private String name;
  private String city;
  private String postalCode;
  private String street;

}
