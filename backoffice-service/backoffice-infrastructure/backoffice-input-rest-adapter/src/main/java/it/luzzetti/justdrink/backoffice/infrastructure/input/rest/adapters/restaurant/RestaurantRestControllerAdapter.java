package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase.CreateRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase.DeleteRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery.ListRestaurantsCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery.ShowRestaurantCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.MenuRestControllerAdapter;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
  private final DeleteRestaurantUseCase deleteRestaurantUseCase;
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
  public ResponseEntity<RestaurantResource> getRestaurant(@PathVariable UUID restaurantId) {
    // Parsing parameters from HTTP
    var command =
        ShowRestaurantCommand.builder().restaurantId(new RestaurantId(restaurantId)).build();

    // Calling Use-Case
    Restaurant theFoundRestaurant = showRestaurantQuery.showRestaurant(command);

    // Crafting a HATEOAS response
    RestaurantResource resource = restaurantWebMapper.toResource(theFoundRestaurant);

    addStandardHateoasLinks(restaurantId, resource);

    return ResponseEntity.ok(resource);
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

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID restaurantId) {
    // Creating the command
    var command = DeleteRestaurantCommand.builder().restaurantId(restaurantId).build();

    // Executing Use-Case
    deleteRestaurantUseCase.deleteRestaurant(command);

    // Returning a NO CONTENT
    return ResponseEntity.noContent().build();
  }

  public record RestaurantCreationRequest(@NotBlank String name) {}

  private static void addStandardHateoasLinks(UUID restaurantId, RestaurantResource resource) {

    Class<RestaurantRestControllerAdapter> thisAdapter = RestaurantRestControllerAdapter.class;
    Class<MenuRestControllerAdapter> menuAdapter = MenuRestControllerAdapter.class;

    resource.add(
        linkTo(methodOn(thisAdapter).getRestaurant(restaurantId))
            .withSelfRel()
            .andAffordance(afford(methodOn(thisAdapter).deleteRestaurant(restaurantId))),
        linkTo(methodOn(menuAdapter).getMenu(restaurantId)).withRel("getMenu"),
        linkTo(thisAdapter).withRel("listRestaurants"));
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
}
