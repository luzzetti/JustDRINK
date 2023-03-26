package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

public interface DeleteRestaurantUseCase {

  void deleteRestaurant(DeleteRestaurantCommand command);

  @Builder
  record DeleteRestaurantCommand(@NotNull UUID restaurantId) {}
}
