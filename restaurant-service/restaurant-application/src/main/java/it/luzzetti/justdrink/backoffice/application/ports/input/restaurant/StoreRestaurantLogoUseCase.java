package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Extension;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface StoreRestaurantLogoUseCase {

  void storeRestaurantLogo(StoreRestaurantLogoCommand command);

  @Builder
  record StoreRestaurantLogoCommand(
      @NotNull RestaurantId restaurantId, @NotNull byte[] file, @NotNull Extension extension) {}
}
