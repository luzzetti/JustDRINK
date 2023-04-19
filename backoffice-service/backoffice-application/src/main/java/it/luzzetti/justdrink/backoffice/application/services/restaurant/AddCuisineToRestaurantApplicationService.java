package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.AddCuisineToRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddCuisineToRestaurantApplicationService implements AddCuisineToRestaurantUseCase {

  private final FindRestaurantPort findRestaurantPort;
  private final SaveRestaurantPort saveRestaurantPort;

  @Override
  @Transactional
  public Restaurant addCuisineToRestaurant(AddCuisineToRestaurantCommand command) {

    Restaurant theRestaurant =
        findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());

    theRestaurant.addCuisine(command.theCuisineToAdd());

    throw new DomainException(RestaurantErrors.NOT_IMPLEMENTED_FEATURE);
    //    return saveRestaurantPort.saveRestaurant(theRestaurant);
  }
}
