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
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.Set;
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

    // Geocoding the Address
    Address theAddress = extractValidAddress(command.addressName(), command.coordinates());

    // Building a Restaurant
    Restaurant aNewRestaurant = generateRestaurant(theAddress, command.name(), command.cuisines());
    Restaurant theCreatedRestaurant = saveRestaurantPort.saveRestaurant(aNewRestaurant);

    // Building a Menu
    Menu aNewMenu = generateMenuForRestaurant(theCreatedRestaurant);
    saveMenuPort.saveMenu(aNewMenu);

    // Building a Worktime
    Worktime aNewWorktime = generateWorktimeForRestaurant(theCreatedRestaurant);
    saveWorktimePort.saveWorktime(aNewWorktime);

    return theCreatedRestaurant;
  }

  private Worktime generateWorktimeForRestaurant(Restaurant theCreatedRestaurant) {
    WorktimeId aGeneratedWorktimeId = generateWorktimeIdPort.nextWorktimeIdentifier();
    return Worktime.builder()
        .id(aGeneratedWorktimeId)
        .restaurantId(theCreatedRestaurant.getId())
        .build();
  }

  private Menu generateMenuForRestaurant(Restaurant theCreatedRestaurant) {
    MenuId aGeneratedMenuId = generateMenuIdPort.nextMenuIdentifier();
    return Menu.builder().id(aGeneratedMenuId).restaurantId(theCreatedRestaurant.getId()).build();
  }

  private Restaurant generateRestaurant(
      Address theAddress, String providedName, Set<Cuisine> providedCuisines) {

    var aGeneratedRestaurantId = generateRestaurantIdPort.generateRestaurantIdentifier();

    return Restaurant.builder()
        .id(aGeneratedRestaurantId)
        .name(providedName)
        .address(theAddress)
        .cuisines(providedCuisines)
        .build();
  }

  private Address extractValidAddress(String addressName, Optional<Coordinates> coordinates) {

    Coordinates correctCoordinates =
        coordinates
            .or(() -> findCoordinatesPort.displayName(addressName))
            .orElseThrow(
                () ->
                    new ElementNotValidException(RestaurantErrors.IMPOSSIBLE_TO_GEOCODE)
                        .putInfo("address", addressName));

    return Address.builder().displayName(addressName).coordinates(correctCoordinates).build();
  }
}
