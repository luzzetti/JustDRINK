package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoUsecase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.UpdateFileRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UploadLogoRestaurantApplicationService implements UploadLogoUsecase {

  private final SaveRestaurantPort saveRestaurantPort;
  private final FindRestaurantPort findRestaurantPort;

  private final UpdateFileRestaurantPort transferLogoRestaurantPort;

  @Override
  public void uploadLogoRestaurant(UploadLogoRetaurantCommand command) {

    Restaurant restaurant = findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());

    String pathLogo = transferLogoRestaurantPort.updateLogo(command);

    restaurant.changeLogo(pathLogo);

    saveRestaurantPort.saveRestaurant(restaurant);
  }
}
