package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.DeleteRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.GenerateRestaurantIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveLogoRestaurantPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.SaveRestaurantPort;
import it.luzzetti.justdrink.backoffice.domain.FileImage;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant.RestaurantBuilder;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.DomainException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class RestaurantJpaAdapter
    implements FindRestaurantPort,
        ListRestaurantsPort,
        SaveRestaurantPort,
        DeleteRestaurantPort,
        GenerateRestaurantIdPort,
        SaveLogoRestaurantPort {
  private final Path rootUploadImage = Paths.get("uploads");

  private final RestaurantJpaRepository restaurantJpaRepository;
  private final RestaurantJpaMapper restaurantJpaMapper;

  @Override
  public Restaurant saveRestaurant(Restaurant aNewRestaurant) {

    // TODO: da fare!!! modifica entity e mapper

    RestaurantJpaEntity aNewEntity = restaurantJpaMapper.toEntity(aNewRestaurant);

    RestaurantJpaEntity theCreatedEntity = restaurantJpaRepository.save(aNewEntity);

    return restaurantJpaMapper.toDomain(theCreatedEntity);
  }

  @Override
  public List<Restaurant> listRestaurants(String filter, Integer maxPageSize, Integer offset) {

    List<RestaurantJpaEntity> restaurants =
        restaurantJpaRepository.findAll(filter, maxPageSize, offset);

    return restaurants.stream().map(restaurantJpaMapper::toDomain).toList();
  }

  @Override
  public Restaurant findRestaurantByIdMandatory(RestaurantId restaurantId) {

    return restaurantJpaRepository
        .findById(restaurantId.id())
        .map(restaurantJpaMapper::toDomain)
        .orElseThrow(
            () -> new DomainException(RestaurantErrors.NOT_FOUND).putInfo("id", restaurantId));
  }

  public Restaurant findRestaurantByName(String restaurantName) {
    return restaurantJpaRepository
        .findRestaurantByName(restaurantName)
        .map(restaurantJpaMapper::toDomain)
        .orElseThrow(
            () -> new DomainException(RestaurantErrors.NOT_FOUND).putInfo("name", restaurantName));
  }

  @Override
  public void deleteRestaurantById(RestaurantId restaurantId) {
    restaurantJpaRepository.deleteById(restaurantId.id());
  }

  @Override
  public RestaurantId generateRestaurantIdentifier() {
    return RestaurantId.from(UUID.randomUUID());
  }

  @Override
  public String saveLogo(FileImage image) {

    initDirectoryUploadImage();

    try(InputStream inputStream = image.getInputStream()) {

      Path path = this.rootUploadImage.resolve(image.getName());

      Files.copy(inputStream, path);

      return path.toUri().toString();
    } catch (Exception e) {

      throw new DomainException(RestaurantErrors.IMPOSSIBLE_TO_UPLOAD);
    }
  }

  private void initDirectoryUploadImage() {
    if (!rootUploadImage.toFile().exists()) {
      try {
        Files.createDirectories(rootUploadImage);
      } catch (IOException e) {
        log.warn(()-> String.format("Impossibile inizializzare la directory per l'upload"));
        throw new DomainException(RestaurantErrors.IMPOSSIBLE_TO_UPLOAD);
      }
    }
  }
}
