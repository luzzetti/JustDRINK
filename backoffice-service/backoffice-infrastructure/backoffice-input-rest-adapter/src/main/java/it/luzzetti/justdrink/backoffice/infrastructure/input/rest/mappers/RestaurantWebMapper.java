package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.RestaurantRestControllerAdapter.RestaurantCreatedResponse;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantWebMapper {
  RestaurantCreatedResponse toResponse(Restaurant theCreatedRestaurant);

  default UUID toUuid(RestaurantId restaurantId) {
    return restaurantId.id();
  }
}
