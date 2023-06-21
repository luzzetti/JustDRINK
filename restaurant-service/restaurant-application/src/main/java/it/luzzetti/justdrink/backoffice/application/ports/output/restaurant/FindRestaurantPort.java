package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.List;

public interface FindRestaurantPort {
  Restaurant findRestaurantByIdMandatory(RestaurantId restaurantId);

  List<Restaurant> findRestaurantByCoordinateContainedInDeliveryArea(Coordinates coordinate);
}
