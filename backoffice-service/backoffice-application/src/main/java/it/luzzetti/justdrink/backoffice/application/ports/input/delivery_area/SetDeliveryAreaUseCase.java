package it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.locationtech.jts.geom.Polygon;

public interface SetDeliveryAreaUseCase {
  DeliveryArea setDeliveryArea(SetDeliveryAreaCommand command);

  @Builder
  record SetDeliveryAreaCommand(@NotNull RestaurantId restaurantId, Polygon polygon) {}
}
