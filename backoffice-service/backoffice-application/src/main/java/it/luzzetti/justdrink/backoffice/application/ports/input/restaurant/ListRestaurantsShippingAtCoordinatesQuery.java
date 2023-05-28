package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.List;
import lombok.Builder;

public interface ListRestaurantsShippingAtCoordinatesQuery {

  List<Restaurant> listRestaurantsShippingAtCoordinates(
      ListRestaurantsShippingAtCoordinatesCommand command);

  @Builder
  record ListRestaurantsShippingAtCoordinatesCommand(
      Coordinates coordinates, int pageSize, int offset) {}
}
