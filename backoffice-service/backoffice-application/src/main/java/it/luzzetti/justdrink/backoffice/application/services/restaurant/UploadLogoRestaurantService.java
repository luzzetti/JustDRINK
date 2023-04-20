package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveLogoRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.FileImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UploadLogoRestaurantService implements UploadLogoRestaurantUseCase {

  private final SaveLogoRestaurantPort saveLogoRestaurantPort;

  @Override
  public String updateLogo(UploadFileImageCommand command) {

    FileImage fileImage = FileImage.builder()
        .restaurantId(command.restaurantId())
        .name(command.name())
        .inputStream(command.inputStream()).build();

    String logoUrl = saveLogoRestaurantPort.saveLogo(fileImage);

    return logoUrl;
  }
}
