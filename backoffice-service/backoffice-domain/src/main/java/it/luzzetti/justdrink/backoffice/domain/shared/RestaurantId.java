package it.luzzetti.justdrink.backoffice.domain.shared;

import java.util.UUID;

public record RestaurantId(UUID id) {
  public static RestaurantId empty() {
    return new RestaurantId(null);
  }

  public static RestaurantId from(UUID restaurantId) {
    return new RestaurantId(restaurantId);
  }
}
