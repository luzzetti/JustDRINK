package it.luzzetti.justdrink.backoffice.application.ports.output;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;

public interface FindRestaurantPort {
  Restaurant findRestaurantByIdMandatory(RestaurantId restaurantId);
}
