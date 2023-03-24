package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.input.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.RestaurantWebMapper;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants")
@Log4j2
@RequiredArgsConstructor
public class RestaurantRestControllerAdapter {

  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final RestaurantWebMapper restaurantWebMapper;

  @PostMapping
  public ResponseEntity<RestaurantCreatedResponse> createRestaurant(
      @RequestBody RestaurantCreationRequest request) {
    // Creating the command
    var command =
        CreateRestaurantUseCase.CreateRestaurantCommand.builder().name(request.name()).build();

    // Executing Use-Case
    Restaurant theCreatedRestaurant = createRestaurantUseCase.createRestaurant(command);

    // Returning a response
    var response = restaurantWebMapper.toResponse(theCreatedRestaurant);
    return ResponseEntity.ok(response);
  }

  record RestaurantCreationRequest(@NotBlank String name) {}

  public record RestaurantCreatedResponse(UUID id, String name, boolean enabled) {}
}
