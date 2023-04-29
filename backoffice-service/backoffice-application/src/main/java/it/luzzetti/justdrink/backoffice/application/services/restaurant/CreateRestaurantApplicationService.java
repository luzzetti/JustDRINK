package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.GenerateMenuIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.GenerateRestaurantIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateWorktimeIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuId;
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

  private final SecurityPort securityPort;

  private final GenerateRestaurantIdPort generateRestaurantIdPort;
  private final GenerateWorktimeIdPort generateWorktimeIdPort;
  private final GenerateMenuIdPort generateMenuIdPort;

  @Override
  @Transactional
  public Restaurant createRestaurant(@Valid CreateRestaurantCommand command) {
    log.debug(() -> "createRestaurant(%s)".formatted(command));

    securityPort.assertThatUserHasPermissionToCreateRestaurant();

    // Fetching / Creating required values
    String displayName = command.addressName();

    Coordinates coordinates =
        command
            .coordinates()
            .or(() -> findCoordinatesPort.findCoordinatesByAddressName(displayName))
            .orElseThrow(
                () ->
                    new ElementNotValidException(RestaurantErrors.IMPOSSIBLE_TO_GEOCODE)
                        .putInfo("address", displayName));

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

    MenuId aGeneratedMenuId = generateMenuIdPort.nextMenuIdentifier();
    Menu aNewMenu =
        Menu.builder().id(aGeneratedMenuId).restaurantId(theCreatedRestaurant.getId()).build();
    Menu theCreatedMenu = saveMenuPort.saveMenu(aNewMenu);

    log.debug(
        () ->
            String.format(
                "The menu %s has been associated with restaurant %s",
                theCreatedMenu.getId(), theCreatedMenu.getRestaurantId()));

    //

    WorktimeId aGeneratedWorktimeId = generateWorktimeIdPort.nextWorktimeIdentifier();
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
