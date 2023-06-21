package it.luzzetti.justdrink.order.domain.shared.typed_ids;

import java.util.UUID;

public record CustomerId(UUID id) {
  public static CustomerId from(UUID orderId) {
    return new CustomerId(orderId);
  }
}
