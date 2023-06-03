package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsDeliveringAtCoordinatesQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsDeliveringAtCoordinatesQuery.ListRestaurantsDeliveringAtCoordinatesCommand;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.ListRestaurantsResponse;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.RestaurantWebMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants/search")
@Log4j2
@RequiredArgsConstructor
public class RestaurantSearchRestControllerAdapter {

  // Queries
  private final ListRestaurantsDeliveringAtCoordinatesQuery
      listRestaurantsDeliveringAtCoordinatesQuery;

  // Mappers
  private final RestaurantWebMapper restaurantWebMapper;

  @Operation(summary = "Mostra la lista dei ristoranti che spediscono alle coordinate fornite")
  @GetMapping("/deliveringAtCoordinates")
  public ResponseEntity<ListRestaurantsResponse> listRestaurantsDeliveringAtCoordinates(
      @RequestParam double latitude,
      @RequestParam double longitude,
      @RequestParam Optional<Integer> pageSize,
      @RequestParam Optional<Integer> pageNumber) {

    Coordinates coordinates = Coordinates.of(Latitude.of(latitude), Longitude.of(longitude));

    var command =
        ListRestaurantsDeliveringAtCoordinatesCommand.builder()
            .coordinates(coordinates)
            .pageSize(pageSize.orElse(10))
            .offset(pageNumber.orElse(0))
            .build();

    // Executing UseCase
    var theFoundRestaurants =
        listRestaurantsDeliveringAtCoordinatesQuery.listRestaurantsDeliveringAtCoordinates(command);

    // Crafting a Response
    var theRestaurantResources =
        theFoundRestaurants.stream().map(restaurantWebMapper::toListElement).toList();
    var response = ListRestaurantsResponse.builder().restaurants(theRestaurantResources).build();
    return ResponseEntity.ok(response);
  }
}
