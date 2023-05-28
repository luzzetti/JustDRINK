package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import java.util.List;
import lombok.Builder;

public interface ListRestaurantsContainingCoordinatesQuery {

  List<Restaurant> listRestaurantsContainingCoordinates(
      ListRestaurantsContainingCoordinatesCommand command);

  @Builder
  record ListRestaurantsContainingCoordinatesCommand(
      double longitude, double latitude, int pageSize, int offset) {}
}
