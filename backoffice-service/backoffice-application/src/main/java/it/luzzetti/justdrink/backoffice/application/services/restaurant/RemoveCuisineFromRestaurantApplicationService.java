package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.RemoveCuisineFromRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class RemoveCuisineFromRestaurantApplicationService implements
    RemoveCuisineFromRestaurantUseCase {

  private final FindRestaurantPort findRestaurantPort;
  private final SaveRestaurantPort saveRestaurantPort;

  @Override
  @Transactional
  public void removeCuisineFromRestaurant(RemoveCuisineFromRestaurantCommand command) {

    Restaurant restaurantByIdMandatory = findRestaurantPort.findRestaurantByIdMandatory(
        command.restaurantId());

    restaurantByIdMandatory.removeCuisine(command.cuisineToRemove());

    saveRestaurantPort.saveRestaurant(restaurantByIdMandatory);
  }
}
