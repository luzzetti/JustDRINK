package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface AddCuisineToRestaurantUseCase {

  Restaurant addCuisineToRestaurant(AddCuisineToRestaurantCommand command);

  @Builder
  record AddCuisineToRestaurantCommand(@NotNull RestaurantId restaurantId, @NotNull Cuisine theCuisineToAdd) {}
}
