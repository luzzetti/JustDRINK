package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.GenerateRestaurantIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateWorktimeIdsPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.WorktimeId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateRestaurantApplicationService implements CreateRestaurantUseCase {

  private final FindCoordinatesPort findCoordinatesPort;
  private final SaveRestaurantPort saveRestaurantPort;
  private final SaveMenuPort saveMenuPort;
  private final SaveWorktimePort saveWorktimePort;
  private final GenerateWorktimeIdsPort generateWorktimeIdsPort;
  private final GenerateRestaurantIdPort generateRestaurantIdPort;

  @Override
  @Transactional
  public Restaurant createRestaurant(@Valid CreateRestaurantCommand command) {

    // Fetching / Creating required values
    String displayName = command.addressName();

    Coordinates coordinates =
        command
            .coordinates()
            .or(() -> findCoordinatesPort.findCoordinatesByAddressName(displayName))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "It has not been possible to retrieve the coordinates related to the address %s"
                            .formatted(displayName)));

    // Calling UseCase
    Address theAddress =
        Address.builder().displayName(displayName).coordinates(coordinates).build();

    var aGeneratedRestaurantId = generateRestaurantIdPort.generateRestaurantIdentifier();

    Restaurant aNewRestaurant =
        Restaurant.builder()
            .id(aGeneratedRestaurantId)
            .name(command.name())
            .address(theAddress)
            .cuisines(command.cuisines())
            .build();

    Restaurant theCreatedRestaurant = saveRestaurantPort.saveRestaurant(aNewRestaurant);

    /*
     * FIXME:
     * Once 'early ID generation' and 'Domain Events' are implemented,
     * Menu and Worktime creation, are probably gonna be moved in their own UseCases
     * uncoupled from here
     */

    Menu aNewMenu = Menu.newMenuForRestaurant(theCreatedRestaurant.getId());
    Menu theCreatedMenu = saveMenuPort.createMenu(aNewMenu);
    log.debug(
        () ->
            String.format(
                "The menu %s has been associated with restaurant %s",
                theCreatedMenu.getId(), theCreatedMenu.getRestaurantId()));

    //
    WorktimeId aGeneratedWorktimeId = generateWorktimeIdsPort.nextWorktimeIdentifier();
    Worktime aNewWorktime =
        Worktime.builder()
            .id(aGeneratedWorktimeId)
            .restaurantId(theCreatedRestaurant.getId())
            .build();
    Worktime theCreatedWorktime = saveWorktimePort.saveWorktime(aNewWorktime);

    log.debug(
        () ->
            String.format(
                "The worktime %s has been associated with restaurant %s",
                theCreatedWorktime.getId(), theCreatedMenu.getRestaurantId()));

    return theCreatedRestaurant;
  }
}
