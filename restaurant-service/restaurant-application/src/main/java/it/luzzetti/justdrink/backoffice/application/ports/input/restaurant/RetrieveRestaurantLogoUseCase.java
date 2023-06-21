package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface RetrieveRestaurantLogoUseCase {

  byte[] retrieveRestaurantLogo(RetrieveRestaurantLogoCommand retrieveRestaurantLogoCommand);

  @Builder
  record RetrieveRestaurantLogoCommand(@NotNull RestaurantId restaurantId) {}
}
