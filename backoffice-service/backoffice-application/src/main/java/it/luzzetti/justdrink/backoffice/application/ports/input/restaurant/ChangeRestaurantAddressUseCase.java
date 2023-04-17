package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.Builder;

public interface ChangeRestaurantAddressUseCase {

  Restaurant changeRestaurantAddress(ChangeRestaurantAddressCommand command);

  @Builder
  record ChangeRestaurantAddressCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull @NotEmpty String addressName,
      Optional<Coordinates> coordinates) {}
}
