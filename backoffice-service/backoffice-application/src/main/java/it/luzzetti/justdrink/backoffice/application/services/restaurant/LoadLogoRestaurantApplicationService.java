package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.LoadLogoRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoadLogoRestaurantApplicationService implements LoadLogoRestaurantUseCase {

  private final FindRestaurantPort findRestaurantPort;
  @Override
  public byte[] loadLogo(LoadLogoCommand loadLogoCommand) throws IOException {

    Restaurant restaurant = findRestaurantPort.findRestaurantByIdMandatory(
        loadLogoCommand.restaurantId());

    String fullPath = restaurant.getLogoPath();

    return Files.readAllBytes(new File(fullPath).toPath());
  }
}
