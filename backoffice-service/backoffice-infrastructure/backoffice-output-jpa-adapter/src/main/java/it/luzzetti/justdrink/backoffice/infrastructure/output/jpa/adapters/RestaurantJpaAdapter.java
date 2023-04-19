package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.DeleteRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.GenerateRestaurantIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.DomainException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RestaurantJpaAdapter
    implements FindRestaurantPort,
        ListRestaurantsPort,
        SaveRestaurantPort,
        DeleteRestaurantPort,
        GenerateRestaurantIdPort {
  private final RestaurantJpaRepository restaurantJpaRepository;
  private final RestaurantJpaMapper restaurantJpaMapper;

  @Override
  public Restaurant saveRestaurant(Restaurant aNewRestaurant) {

    // TODO: da fare!!! modifica entity e mapper

    RestaurantJpaEntity aNewEntity = restaurantJpaMapper.toEntity(aNewRestaurant);

    RestaurantJpaEntity theCreatedEntity = restaurantJpaRepository.save(aNewEntity);

    return restaurantJpaMapper.toDomain(theCreatedEntity);
  }

  @Override
  public List<Restaurant> listRestaurants(String filter, Integer maxPageSize, Integer offset) {

    List<RestaurantJpaEntity> restaurants =
        restaurantJpaRepository.findAll(filter, maxPageSize, offset);

    return restaurants.stream().map(restaurantJpaMapper::toDomain).toList();
  }

  @Override
  public Restaurant findRestaurantByIdMandatory(RestaurantId restaurantId) {

    return restaurantJpaRepository
        .findById(restaurantId.id())
        .map(restaurantJpaMapper::toDomain)
        .orElseThrow(
            () -> new DomainException(RestaurantErrors.NOT_FOUND).putInfo("id", restaurantId));
  }

  public Restaurant findRestaurantByName(String restaurantName) {
    return restaurantJpaRepository
        .findRestaurantByName(restaurantName)
        .map(restaurantJpaMapper::toDomain)
        .orElseThrow(
            () -> new DomainException(RestaurantErrors.NOT_FOUND).putInfo("name", restaurantName));
  }

  @Override
  public void deleteRestaurantById(RestaurantId restaurantId) {
    restaurantJpaRepository.deleteById(restaurantId.id());
  }

  @Override
  public RestaurantId generateRestaurantIdentifier() {
    return RestaurantId.from(UUID.randomUUID());
  }
}
