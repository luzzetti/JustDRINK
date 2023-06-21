package it.luzzetti.justdrink.customer.domain.shared.typed_ids;

import java.util.UUID;

public record CustomerId(UUID id) {
  public static CustomerId from(UUID uuid) {
    return new CustomerId(uuid);
  }
}
