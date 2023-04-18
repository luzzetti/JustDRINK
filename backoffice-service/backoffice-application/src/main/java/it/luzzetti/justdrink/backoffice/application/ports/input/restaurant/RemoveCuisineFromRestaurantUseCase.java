package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import jakarta.validation.constraints.NotNull;

public interface RemoveCuisineFromRestaurantUseCase {

  void removeCuisineFromRestaurant(RemoveCuisineFromRestaurantCommand command);

  record RemoveCuisineFromRestaurantCommand(@NotNull RestaurantId restaurantId, @NotNull Cuisine cuisineToRemove) {}
}
