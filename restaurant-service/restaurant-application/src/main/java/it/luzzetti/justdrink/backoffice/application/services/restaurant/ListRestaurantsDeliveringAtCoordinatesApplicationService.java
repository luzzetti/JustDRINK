package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsDeliveringAtCoordinatesQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListRestaurantsDeliveringAtCoordinatesApplicationService
    implements ListRestaurantsDeliveringAtCoordinatesQuery {

  private final FindRestaurantPort findRestaurantPort;

  @Override
  public List<Restaurant> listRestaurantsDeliveringAtCoordinates(
      ListRestaurantsDeliveringAtCoordinatesCommand command) {

    return findRestaurantPort.findRestaurantByCoordinateContainedInDeliveryArea(
        command.coordinates());
  }
}
