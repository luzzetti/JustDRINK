package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.RestaurantRestControllerAdapter;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantWebMapper {
  RestaurantRestControllerAdapter.RestaurantCreatedResponse toResponse(
      Restaurant theCreatedRestaurant);

  default UUID toUuid(Restaurant.RestaurantId restaurantId) {
    return restaurantId.id();
  }
}
