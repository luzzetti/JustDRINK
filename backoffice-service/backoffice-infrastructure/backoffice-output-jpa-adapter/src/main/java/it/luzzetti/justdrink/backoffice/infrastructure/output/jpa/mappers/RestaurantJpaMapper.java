package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantJpaMapper {
  Restaurant toDomain(RestaurantJpaEntity theCreatedEntity);

  RestaurantJpaEntity toEntity(Restaurant aNewRestaurant);

  default UUID map(Restaurant.RestaurantId restaurantId) {
    return restaurantId.id();
  }

  default Restaurant.RestaurantId map(UUID uuid) {
    return new Restaurant.RestaurantId(uuid);
  }
}
