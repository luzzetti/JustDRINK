package it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface ShowDeliveryAreaForRestaurantQuery {
  DeliveryArea showDeliveryArea(ShowDeliveryAreaCommand command);

  @Builder
  record ShowDeliveryAreaCommand(@NotNull RestaurantId restaurantId) {}
}
