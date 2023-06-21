package it.luzzetti.justdrink.backoffice.application.ports.output.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;

public interface FindMenuPort {
  Menu findMenuByRestaurantIdMandatory(RestaurantId restaurantId);
}
