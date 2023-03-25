package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.CreateRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.QRestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements ListRestaurantsPort, CreateRestaurantPort {
  private final RestaurantJpaRepository restaurantJpaRepository;
  private final RestaurantJpaMapper restaurantJpaMapper;

  @Override
  public Restaurant createRestaurant(Restaurant aNewRestaurant) {
    RestaurantJpaEntity aNewEntity = restaurantJpaMapper.toEntity(aNewRestaurant);

    RestaurantJpaEntity theCreatedEntity = restaurantJpaRepository.save(aNewEntity);

    return restaurantJpaMapper.toDomain(theCreatedEntity);
  }

  @Override
  public List<Restaurant> listRestaurants(String filter, Integer maxPageSize, Integer offset) {
    var root = QRestaurantJpaEntity.restaurantJpaEntity;

    List<RestaurantJpaEntity> restaurants =
        (List<RestaurantJpaEntity>)
            restaurantJpaRepository.findAll(root.name.containsIgnoreCase(filter));

    return restaurants.stream().map(restaurantJpaMapper::toDomain).toList();
  }
}
