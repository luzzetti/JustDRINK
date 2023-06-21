package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.StoreRestaurantLogoUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.StoreRestaurantLogoPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoreRestaurantLogoApplicationService implements StoreRestaurantLogoUseCase {

  private final SaveRestaurantPort saveRestaurantPort;
  private final FindRestaurantPort findRestaurantPort;

  private final StoreRestaurantLogoPort storeRestaurantLogoPort;

  @Override
  public void storeRestaurantLogo(StoreRestaurantLogoCommand command) {

    Restaurant theRestaurant =
        findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());

    String theLogoPath =
        storeRestaurantLogoPort.storeRestaurantLogo(command.restaurantId(), command.file(), command.extension());

    theRestaurant.changeLogo(theLogoPath);

    saveRestaurantPort.saveRestaurant(theRestaurant);
  }
}
