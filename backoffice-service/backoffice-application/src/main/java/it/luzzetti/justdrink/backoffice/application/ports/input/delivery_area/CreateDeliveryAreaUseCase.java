package it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import lombok.Builder;
import org.locationtech.jts.geom.Polygon;

public interface CreateDeliveryAreaUseCase {
  DeliveryArea createDeliveryArea(CreateDeliveryAreaCommand command);

  @Builder
  record CreateDeliveryAreaCommand(RestaurantId restaurantId, Polygon polygon) {}
}
