package it.luzzetti.justdrink.customer.domain.aggregates;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import lombok.Builder;

@Builder
public class Customer {

  private CustomerId id;
}
