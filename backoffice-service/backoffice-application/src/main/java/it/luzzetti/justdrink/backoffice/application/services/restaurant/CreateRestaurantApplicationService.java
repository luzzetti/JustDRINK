package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.CreateMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.CreateRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateRestaurantApplicationService implements CreateRestaurantUseCase {
  private final CreateRestaurantPort createRestaurantPort;
  private final CreateMenuPort createMenuPort;

  @Override
  @Transactional
  public Restaurant createRestaurant(@Valid CreateRestaurantCommand command) {

    // Use-Case
    Restaurant aNewRestaurant = Restaurant.builder().name(command.name()).build();
    Restaurant theCreatedRestaurant = createRestaurantPort.createRestaurant(aNewRestaurant);

    Menu aNewMenu = Menu.newMenuForRestaurant(theCreatedRestaurant.getId());
    Menu theCreatedMenu = createMenuPort.createMenu(aNewMenu);

    log.debug(
        () ->
            String.format(
                "The menu %s has been associated with restaurant %s",
                theCreatedMenu.getId(), theCreatedMenu.getRestaurantId()));

    return theCreatedRestaurant;
  }
}
