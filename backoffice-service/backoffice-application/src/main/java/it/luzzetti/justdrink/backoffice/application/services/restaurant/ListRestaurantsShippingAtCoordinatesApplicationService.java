package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsShippingAtCoordinatesQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindDeliveryAreasPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListRestaurantsShippingAtCoordinatesApplicationService implements
    ListRestaurantsShippingAtCoordinatesQuery {

  private final FindCoordinatesPort findCoordinatesPort;
  private final ListRestaurantsPort listRestaurantsPort;
  private final FindDeliveryAreasPort findDeliveryAreasPort;

  @Override
  public List<Restaurant> listRestaurantsShippingAtCoordinates(
      ListRestaurantsShippingAtCoordinatesCommand command) {

//    Coordinates coordinates = searchCoordinates(command.addressName(), command.coordinates());

    List<DeliveryArea> areasContainingTheCoordinates =
        findDeliveryAreasPort.findDeliveryAreasContainingCoordinates(command.coordinates());

    List<RestaurantId> restaurantIds =
        areasContainingTheCoordinates.stream().map(DeliveryArea::getRestaurantId).toList();

    log.warn(
        () -> String.format("ID ristoranti che possono portarti il vino a casa %s", restaurantIds));

    List<Restaurant> restaurants = listRestaurantsPort.listRestaurantsByIds(restaurantIds);

    return restaurants;
  }

  private Coordinates searchCoordinates(String addressName, Optional<Coordinates> coordinates) {
    return coordinates
        .or(() -> findCoordinatesPort.displayName(addressName))
        .orElseThrow(
            () ->
                new ElementNotValidException(RestaurantErrors.IMPOSSIBLE_TO_GEOCODE)
                    .putInfo("address", addressName));
  }
}
