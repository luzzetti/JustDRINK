package it.luzzetti.justdrink.order.domain.shared.typed_ids;

import java.util.UUID;

public record ProductId(UUID id) {
  public static ProductId from(UUID orderId) {
    return new ProductId(orderId);
  }
}
