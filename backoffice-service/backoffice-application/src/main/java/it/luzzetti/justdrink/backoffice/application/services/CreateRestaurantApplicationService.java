package it.luzzetti.justdrink.backoffice.application.services;

import it.luzzetti.justdrink.backoffice.application.ports.input.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.CreateRestaurantPort;
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

  @Override
  @Transactional
  public Restaurant createRestaurant(@Valid CreateRestaurantCommand command) {

    // Use-Case
    Restaurant aNewRestaurant = Restaurant.builder().name(command.name()).build();

    return createRestaurantPort.createRestaurant(aNewRestaurant);
  }
}
