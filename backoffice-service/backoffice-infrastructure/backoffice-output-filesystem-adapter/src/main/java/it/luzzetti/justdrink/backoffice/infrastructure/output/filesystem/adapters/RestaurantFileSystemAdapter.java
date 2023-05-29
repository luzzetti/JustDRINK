package it.luzzetti.justdrink.backoffice.infrastructure.output.filesystem.adapters;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.StoreRestaurantLogoPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Extension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RestaurantFileSystemAdapter implements StoreRestaurantLogoPort {

  @Value("${filesystem.upload.restaurant:uploadsDefault}")
  private String directoryFileSystem;

  @Override
  public String storeRestaurantLogo(
      RestaurantId restaurantId, byte[] content, Extension extension) {

    Path path = Path.of(directoryFileSystem, restaurantId.id().toString());

    createPathIfNotExistent(path);

    Path fileName = path.resolve("logo%s".formatted(extension.getValue()));

    try {
      Files.write(fileName, content);
      return fileName.toString();
    } catch (IOException exception) {
      throw new ElementNotProcessableException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }
  }

  private void createPathIfNotExistent(Path path) {
    if (Files.exists(path)) {
      return;
    }
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new ElementNotProcessableException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }
  }
}
