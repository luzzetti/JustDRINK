package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.FindRestaurantsByCliendAdressUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindDeliveryAreasPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class FindRestaurantsByCliendAdressService implements FindRestaurantsByCliendAdressUseCase {

  private final FindCoordinatesPort findCoordinatesPort;
  private final ListRestaurantsPort listRestaurantsPort;
  private final FindDeliveryAreasPort findDeliveryAreasPort;

  @Override
  public List<Restaurant> findRestaurantbyClientAdress(
      FindRestaurantsByClientAdressCommand command) {

    Coordinates coordinates = searchCoordinates(command.addressName(), command.coordinates());

    findDeliveryAreasPort.findDeliveryAreasByClientAdress(coordinates);

//    listRestaurantsPort.listRestaurantsByClientAdress(coordinates);

    return null;
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
