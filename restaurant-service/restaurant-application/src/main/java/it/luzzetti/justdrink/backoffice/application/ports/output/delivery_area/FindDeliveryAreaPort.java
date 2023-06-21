package it.luzzetti.justdrink.backoffice.application.ports.output.delivery_area;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;

public interface FindDeliveryAreaPort {

  DeliveryArea findDeliveryAreaByRestaurantId(RestaurantId restaurantId);
}
