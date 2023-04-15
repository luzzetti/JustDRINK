package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.List;
import java.util.Optional;

public interface CustomRestaurantJpaRepository {
  List<RestaurantJpaEntity> findAll(String filter, Integer maxPageSize, Integer offset);

  Optional<RestaurantJpaEntity> findRestaurantByName(String restaurantName);
}
