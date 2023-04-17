package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ChangeRestaurantAddressUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UpdateRestaurantApplicationService implements ChangeRestaurantAddressUseCase {

  private final FindRestaurantPort findRestaurantPort;
  private final FindCoordinatesPort findCoordinatesPort;
  private final SaveRestaurantPort saveRestaurantPort;

  @Override
  @Transactional
  public Restaurant changeRestaurantAddress(ChangeRestaurantAddressCommand command) {

    // Fetching Resources
    Restaurant theFoundRestaurant =
        findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());

    String displayName = command.addressName();

    Coordinates coordinates =
        command
            .coordinates()
            .or(() -> findCoordinatesPort.findCoordinatesByAddressName(displayName))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "It has not been possible to retrieve the coordinates related to this address"));

    // Calling UseCase
    Address theNewAddress = Address.builder().displayName(displayName).coordinates(coordinates).build();
    theFoundRestaurant.changeAddress(theNewAddress);

    // Saving Resources
    return saveRestaurantPort.saveRestaurant(theFoundRestaurant);
  }
}
