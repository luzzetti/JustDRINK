package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;

public interface GenerateRestaurantIdPort {
  RestaurantId generateRestaurantIdentifier();
}
