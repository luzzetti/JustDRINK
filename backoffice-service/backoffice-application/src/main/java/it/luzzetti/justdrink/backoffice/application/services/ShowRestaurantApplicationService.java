package it.luzzetti.justdrink.backoffice.application.services;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShowRestaurantApplicationService implements ShowRestaurantQuery {

  private final FindRestaurantPort findRestaurantPort;

  @Override
  public Restaurant showRestaurant(ShowRestaurantCommand command) {
    log.debug(() -> String.format("showRestaurant(%s)", command));

    return findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());
  }
}
