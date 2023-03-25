package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.AggregateRoot;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import lombok.*;

@AggregateRoot
@Getter
@Builder
public class Restaurant {
  private final RestaurantId id = RestaurantId.empty();
  @Getter private final String name;
  private final Boolean enabled = Boolean.FALSE;

  public Restaurant(String name) {
    this.name = name;
  }

  public static Restaurant newRestaurant(String name) {
    return new Restaurant(name);
  }
}
