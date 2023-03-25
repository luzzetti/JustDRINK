package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.input.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.CreateRestaurantUseCase.CreateRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.ListRestaurantsQuery.ListRestaurantsCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.RestaurantWebMapper;
import jakarta.validation.constraints.NotBlank;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants")
@Log4j2
@RequiredArgsConstructor
public class RestaurantRestControllerAdapter {

  private final ListRestaurantsQuery listRestaurantsQuery;
  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final RestaurantWebMapper restaurantWebMapper;

  @GetMapping
  public ResponseEntity<ListRestaurantsResponse> searchRestaurant(
      @RequestBody ListRestaurantsRequest request) {

    // Parsing values
    String filter = request.filter().orElse("");
    Integer maxPageSize = request.maxPageSize().orElse(10);
    Integer offset = request.pageToken().map(PageTokenCodec::decode).orElse(5);

    // Creating command
    var command =
        ListRestaurantsCommand.builder()
            .filter(filter)
            .maxPageSize(maxPageSize)
            .offset(offset)
            .build();

    // Calling UseCase
    var restaurants =
        listRestaurantsQuery.listRestaurants(command).stream()
            .map(restaurantWebMapper::toListElement)
            .toList();

    // Crafting a response
    var response =
        ListRestaurantsResponse.builder()
            .restaurants(restaurants)
            .nextPageToken(PageTokenCodec.encode(offset + restaurants.size()))
            .build();
    return ResponseEntity.ok(response);
  }

  private record PageTokenCodec() {
    static Integer decode(String encodedString) {
      if (encodedString == null) {
        return 0;
      }
      String decodedString = new String(Base64.getDecoder().decode(encodedString));
      return Integer.parseInt(decodedString);
    }

    static String encode(Integer decodedValue) {
      String decodedString = decodedValue.toString();
      return Base64.getEncoder().encodeToString(decodedString.getBytes());
    }
  }

  public record ListRestaurantsRequest(
      Optional<String> filter, Optional<Integer> maxPageSize, Optional<String> pageToken) {}

  @Builder
  public record ListRestaurantsResponse(
      List<RestaurantListElement> restaurants, String nextPageToken) {}

  public record RestaurantListElement(UUID id, String name) {}

  @PostMapping
  public ResponseEntity<RestaurantCreatedResponse> createRestaurant(
      @RequestBody RestaurantCreationRequest request) {
    // Creating the command
    var command = CreateRestaurantCommand.builder().name(request.name()).build();

    // Executing Use-Case
    Restaurant theCreatedRestaurant = createRestaurantUseCase.createRestaurant(command);

    // Returning a response
    var response = restaurantWebMapper.toResponse(theCreatedRestaurant);
    return ResponseEntity.ok(response);
  }

  public record RestaurantCreationRequest(@NotBlank String name) {}

  public record RestaurantCreatedResponse(UUID id, String name, boolean enabled) {}
}
