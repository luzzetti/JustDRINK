package it.luzzetti.justdrink.backoffice.infrastructure.output.filesystem.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoUsecase.UploadLogoRetaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.UpdateFileRestaurantPort;
import it.luzzetti.justdrink.backoffice.infrastructure.output.filesystem.adapters.shared.Directory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
@PropertySource(value = "filesystem.properties")
public class RestaurantFileSystemAdapter implements UpdateFileRestaurantPort {

  @Value("${filesystem.upload.restaurant:uploadsDefault}")
  private String directoryFileSystem;

  @Override
  public String updateLogo(UploadLogoRetaurantCommand command) {

    Path root = Paths.get(directoryFileSystem);

    Path path = root.resolve(command.restaurantId().id().toString()).resolve("logo");

    Directory.init(path);

    String milliseconds = String.valueOf(System.currentTimeMillis());

    String fullPath = path.resolve(milliseconds).toString();

    try (FileOutputStream fos = new FileOutputStream(fullPath)) {
      fos.write(command.file());
      return fullPath;
    } catch (IOException e) {

      throw new RuntimeException(String.format("Impossibile caricare il logo: %s", e.getMessage()));
    }
  }
}
