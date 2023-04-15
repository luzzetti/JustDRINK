package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface DeleteRestaurantUseCase {

  void deleteRestaurant(DeleteRestaurantCommand command);

  @Builder
  record DeleteRestaurantCommand(@NotNull RestaurantId restaurantId) {}
}
