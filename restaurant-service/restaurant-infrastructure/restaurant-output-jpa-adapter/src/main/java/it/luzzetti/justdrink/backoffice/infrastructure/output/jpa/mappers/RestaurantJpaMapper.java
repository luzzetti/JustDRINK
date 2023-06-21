package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(uses = {AddressJpaMapper.class, OwnerJpaMapper.class, CuisineJpaMapper.class})
public interface RestaurantJpaMapper {
  Restaurant toDomain(RestaurantJpaEntity theCreatedEntity);

  RestaurantJpaEntity toEntity(Restaurant aNewRestaurant);

  default UUID map(RestaurantId restaurantId) {
    return restaurantId.id();
  }

  default RestaurantId map(UUID uuid) {
    return RestaurantId.from(uuid);
  }
}
