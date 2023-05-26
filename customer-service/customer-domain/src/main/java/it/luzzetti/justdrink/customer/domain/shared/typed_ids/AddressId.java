package it.luzzetti.justdrink.customer.domain.shared.typed_ids;

import java.util.UUID;

public record AddressId(UUID id) {
  public static AddressId from(UUID uuid) {
    return new AddressId(uuid);
  }
}
