package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.RestaurantListElement;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.RestaurantResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(uses = {AddressWebMapper.class})
public interface RestaurantWebMapper {
  RestaurantResource toResource(Restaurant theCreatedRestaurant);

  RestaurantListElement toListElement(Restaurant theCreatedRestaurant);

  default UUID toUuid(RestaurantId restaurantId) {
    return restaurantId.id();
  }
}
