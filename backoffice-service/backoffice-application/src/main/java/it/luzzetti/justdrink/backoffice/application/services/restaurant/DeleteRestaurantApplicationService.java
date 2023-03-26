package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.DeleteMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.DeleteRestaurantPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteRestaurantApplicationService implements DeleteRestaurantUseCase {
  private final DeleteRestaurantPort deleteRestaurantPort;
  private final DeleteMenuPort deleteMenuPort;

  @Override
  @Transactional
  public void deleteRestaurant(DeleteRestaurantCommand command) {
    deleteMenuPort.deleteMenuByRestaurantId(command.restaurantId());
    deleteRestaurantPort.deleteRestaurantById(command.restaurantId());
  }
}
