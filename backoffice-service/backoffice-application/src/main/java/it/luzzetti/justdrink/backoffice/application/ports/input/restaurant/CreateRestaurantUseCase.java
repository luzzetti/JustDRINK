package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface CreateRestaurantUseCase {

  Restaurant createRestaurant(CreateRestaurantCommand command);

  @Builder
  record CreateRestaurantCommand(@NotNull @NotBlank String name) {}
}
