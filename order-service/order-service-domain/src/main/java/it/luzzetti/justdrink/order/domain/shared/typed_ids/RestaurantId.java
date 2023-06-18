package it.luzzetti.justdrink.order.domain.shared.typed_ids;

import java.util.UUID;

public record RestaurantId(UUID id) {
  public static RestaurantId from(UUID orderId) {
    return new RestaurantId(orderId);
  }
}
