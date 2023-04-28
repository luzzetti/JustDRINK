package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoUsecase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
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
  @Override
  public void uploadLogoRestaurant(UploadLogoRetaurantCommand uploadLogoRetaurantCommand) {

    Restaurant restaurant = findRestaurantPort.findRestaurantByIdMandatory(
        uploadLogoRetaurantCommand.restaurantId());

    Restaurant restaurantWithLogo = restaurant.toBuilder().logoPath(uploadLogoRetaurantCommand.path()).build();

    saveRestaurantPort.saveRestaurant(restaurantWithLogo);

  }
}
