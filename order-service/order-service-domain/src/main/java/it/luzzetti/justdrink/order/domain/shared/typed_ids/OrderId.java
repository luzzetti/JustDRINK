package it.luzzetti.justdrink.order.domain.shared.typed_ids;

import java.util.UUID;

public record OrderId(UUID id) {
  public static OrderId from(UUID orderId) {
    return new OrderId(orderId);
  }
}
