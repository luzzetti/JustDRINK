package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;

public interface CreateRestaurantUseCase {

  Restaurant createRestaurant(CreateRestaurantCommand command);

  @Builder
  record CreateRestaurantCommand(
      @NotNull @NotBlank String name,
      @NotNull @NotEmpty String addressName,
      Optional<Coordinates> coordinates,
      Set<Cuisine> cuisines) {}
}
