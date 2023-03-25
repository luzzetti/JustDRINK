package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import it.luzzetti.justdrink.backoffice.application.ports.input.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.CreateRestaurantUseCase.CreateRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.ListRestaurantsQuery.ListRestaurantsCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.ShowRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.ShowRestaurantQuery.ShowRestaurantCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants")
@Log4j2
@RequiredArgsConstructor
public class RestaurantRestControllerAdapter {

  private final ListRestaurantsQuery listRestaurantsQuery;
  private final ShowRestaurantQuery showRestaurantQuery;
  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final RestaurantWebMapper restaurantWebMapper;

  @GetMapping
  public ResponseEntity<ListRestaurantsResponse> listRestaurants(
      @RequestParam Optional<String> filter,
      @RequestParam Optional<Integer> maxPageSize,
      @RequestParam Optional<String> pageToken) {

    // Parsing values into a command
    Integer offset = pageToken.map(PageTokenCodec::decode).orElse(0);
    var command =
        ListRestaurantsCommand.builder()
            .filter(filter.orElse(""))
            .maxPageSize(maxPageSize.orElse(10))
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

  @GetMapping("/{restaurantId}")
  public ResponseEntity<RestaurantResource> showRestaurant(@PathVariable UUID restaurantId) {
    // Parsing parameters from HTTP
    var command =
        ShowRestaurantCommand.builder().restaurantId(RestaurantId.of(restaurantId)).build();

    // Calling Use-Case
    Restaurant theFoundRestaurant = showRestaurantQuery.showRestaurant(command);

    // Crafting a HATEOAS response
    RestaurantResource resource = restaurantWebMapper.toResource(theFoundRestaurant);
    addStandardHateoasLinks(restaurantId, resource);

    return ResponseEntity.ok(resource);
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

  @Builder
  public record ListRestaurantsResponse(
      List<RestaurantListElement> restaurants, String nextPageToken) {}

  public record RestaurantListElement(UUID id, String name) {}

  @PostMapping
  public ResponseEntity<RestaurantResource> createRestaurant(
      @RequestBody RestaurantCreationRequest request) {
    // Creating the command
    var command = CreateRestaurantCommand.builder().name(request.name()).build();

    // Executing Use-Case
    Restaurant theCreatedRestaurant = createRestaurantUseCase.createRestaurant(command);

    // Crafting and returning a resource with HATEOAS
    RestaurantResource resource = restaurantWebMapper.toResource(theCreatedRestaurant);
    addStandardHateoasLinks(theCreatedRestaurant.getId().id(), resource);

    return ResponseEntity.ok(resource);
  }

  public record RestaurantCreationRequest(@NotBlank String name) {}

  private static void addStandardHateoasLinks(UUID restaurantId, RestaurantResource resource) {
    resource.add(
        linkTo(methodOn(RestaurantRestControllerAdapter.class).showRestaurant(restaurantId))
            .withSelfRel(),
        linkTo(RestaurantRestControllerAdapter.class).withRel("listRestaurants"));
  }
}
