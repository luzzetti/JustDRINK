package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Extension;

public interface StoreRestaurantLogoPort {

  String storeRestaurantLogo(RestaurantId restaurantId, byte[] file, Extension extension);
}
