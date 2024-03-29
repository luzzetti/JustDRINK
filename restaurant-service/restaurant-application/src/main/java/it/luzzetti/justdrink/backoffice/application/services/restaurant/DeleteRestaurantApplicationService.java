package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.DeleteMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.DeleteRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.DeleteWorktimePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteRestaurantApplicationService implements DeleteRestaurantUseCase {

  private final DeleteRestaurantPort deleteRestaurantPort;
  private final FindRestaurantPort findRestaurantPort;

  private final DeleteMenuPort deleteMenuPort;
  private final DeleteWorktimePort deleteWorktimePort;

  private final SecurityPort securityPort;

  @Override
  @Transactional
  public void deleteRestaurant(DeleteRestaurantCommand command) {

    var theRestaurant = findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());
    // TODO: Reimplementare dopo aver fixato i Roles
    // securityPort.assertThatUserHasPermissionToDeleteRestaurant(theRestaurant);

    deleteMenuPort.deleteMenuByRestaurantId(command.restaurantId());
    deleteWorktimePort.deleteWorktimeByRestaurantId(command.restaurantId());
    deleteRestaurantPort.deleteRestaurantById(command.restaurantId());
  }
}
