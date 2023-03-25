package it.luzzetti.justdrink.backoffice.domain.shared;

import java.util.UUID;

public record RestaurantId(UUID id) {
  public static RestaurantId empty() {
    return new RestaurantId(null);
  }

  public static RestaurantId of(UUID uuid) {
    return new RestaurantId(uuid);
  }
}
