package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface ShowRestaurantQuery {

  Restaurant showRestaurant(ShowRestaurantCommand command);

  @Builder
  record ShowRestaurantCommand(@NotNull RestaurantId restaurantId) {}
}
