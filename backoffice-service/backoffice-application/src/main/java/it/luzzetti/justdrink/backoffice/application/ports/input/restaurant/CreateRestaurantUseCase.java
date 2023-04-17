package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.Builder;

public interface CreateRestaurantUseCase {

  Restaurant createRestaurant(CreateRestaurantCommand command);

  @Builder
  record CreateRestaurantCommand(
      @NotNull @NotBlank String name,
      @NotNull @NotEmpty String addressName,
      Optional<Coordinates> coordinates) {}
}
