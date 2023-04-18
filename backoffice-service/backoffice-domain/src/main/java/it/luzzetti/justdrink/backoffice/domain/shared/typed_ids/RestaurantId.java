package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record RestaurantId(UUID id) {
  public static RestaurantId from(UUID restaurantId) {
    return new RestaurantId(restaurantId);
  }
}
