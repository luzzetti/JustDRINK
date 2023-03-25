package it.luzzetti.justdrink.backoffice.domain.shared;

import java.util.UUID;

public record RestaurantId(UUID id) {

  private RestaurantId() {
    this(null);
  }

  public static RestaurantId empty() {
    return new RestaurantId(null);
  }
}
