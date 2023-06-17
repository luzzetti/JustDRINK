package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.commons.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.DeleteRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.GenerateRestaurantIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
    RestaurantJpaEntity aNewEntity = restaurantJpaMapper.toEntity(aNewRestaurant);

    RestaurantJpaEntity theCreatedEntity = restaurantJpaRepository.save(aNewEntity);

    return restaurantJpaMapper.toDomain(theCreatedEntity);
  }

  @Override
  public List<Restaurant> listRestaurants(String filter, Integer pageSize, Integer offset) {

    List<RestaurantJpaEntity> restaurants =
        restaurantJpaRepository.findAll(filter, pageSize, offset);

    return restaurants.stream().map(restaurantJpaMapper::toDomain).toList();
  }

  @Override
  public Restaurant findRestaurantByIdMandatory(RestaurantId restaurantId) {

    return restaurantJpaRepository
        .findById(restaurantId.id())
        .map(restaurantJpaMapper::toDomain)
        .orElseThrow(
            () ->
                new ElementNotFoundException(RestaurantErrors.NOT_FOUND)
                    .putInfo("id", restaurantId));
  }

  @Override
  public List<Restaurant> findRestaurantByCoordinateContainedInDeliveryArea(
      Coordinates coordinate) {

    /*
     * Converto le mie coordinate, in un Point di JTS
     * Ci ha detto culo che viene utilizzato lo stesso sistema di coordinate.
     */
    GeometryFactory gm = new GeometryFactory();

    // Nota: JTS vuole prima la LONGITUDE poi la LATITUDE
    Point point =
        gm.createPoint(
            new Coordinate(
                coordinate.longitude().longitudeValue(), coordinate.latitude().latitudeValue()));

    return restaurantJpaRepository.findByPointContainedInDeliveryArea(point).stream()
        .map(restaurantJpaMapper::toDomain)
        .toList();
  }

  public Restaurant findRestaurantByName(String restaurantName) {
    return restaurantJpaRepository
        .findRestaurantByName(restaurantName)
        .map(restaurantJpaMapper::toDomain)
        .orElseThrow(
            () ->
                new ElementNotFoundException(RestaurantErrors.NOT_FOUND)
                    .putInfo("name", restaurantName));
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
