package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.RetrieveRestaurantLogoUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RetrieveRestaurantLogoApplicationService implements RetrieveRestaurantLogoUseCase {

  private final FindRestaurantPort findRestaurantPort;

  @Override
  public byte[] retrieveRestaurantLogo(RetrieveRestaurantLogoCommand command) {

    Restaurant restaurant = findRestaurantPort.findRestaurantByIdMandatory(command.restaurantId());

    String fullPath = restaurant.getLogoPath();

    try {
      return Files.readAllBytes(Path.of(fullPath));
    } catch (IOException exception) {
      throw new ElementNotProcessableException(RestaurantErrors.LOGO_DOWNLOAD_IMPOSSIBLE);
    }
  }
}
