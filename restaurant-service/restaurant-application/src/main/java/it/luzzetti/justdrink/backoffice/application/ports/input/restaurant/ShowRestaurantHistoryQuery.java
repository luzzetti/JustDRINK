package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;
import lombok.Builder;

public interface ShowRestaurantHistoryQuery {

  Map<Instant, Restaurant> showRestaurantHistory(ShowRestaurantHistoryCommand command);

  @Builder
  record ShowRestaurantHistoryCommand(@NotNull RestaurantId restaurantId) {}
}
