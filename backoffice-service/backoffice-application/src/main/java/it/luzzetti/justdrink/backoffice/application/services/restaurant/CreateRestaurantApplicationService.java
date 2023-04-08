package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateRestaurantApplicationService implements CreateRestaurantUseCase {

  private final SaveRestaurantPort saveRestaurantPort;
  private final SaveMenuPort saveMenuPort;
  private final SaveWorktimePort saveWorktimePort;

  @Override
  @Transactional
  public Restaurant createRestaurant(@Valid CreateRestaurantCommand command) {

    // Use-Case
    Restaurant aNewRestaurant = Restaurant.builder().name(command.name()).build();
    Restaurant theCreatedRestaurant = saveRestaurantPort.createRestaurant(aNewRestaurant);

    Menu aNewMenu = Menu.newMenuForRestaurant(theCreatedRestaurant.getId());
    Menu theCreatedMenu = saveMenuPort.createMenu(aNewMenu);
    log.debug(
        () ->
            String.format(
                "The menu %s has been associated with restaurant %s",
                theCreatedMenu.getId(), theCreatedMenu.getRestaurantId()));

    Worktime aNewWorktime = Worktime.newWorktimeForRestaurant(theCreatedRestaurant.getId());
    Worktime theCreatedWorktime = saveWorktimePort.saveWorktime(aNewWorktime);
    log.debug(
        () ->
            String.format(
                "The worktime %s has been associated with restaurant %s",
                theCreatedWorktime.getId(), theCreatedMenu.getRestaurantId()));

    return theCreatedRestaurant;
  }
}
