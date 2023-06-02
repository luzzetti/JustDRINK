package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ChangeRestaurantAddressUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ChangeRestaurantAddressUseCase.ChangeRestaurantAddressCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery.ShowRestaurantCommand;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.AddressResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.AddressWebMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants/{restaurantId}/address")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class RestaurantAddressRestControllerAdapter {

  // UseCases
  private final ChangeRestaurantAddressUseCase changeRestaurantAddressUseCase;

  // Queries
  private final ShowRestaurantQuery showRestaurantQuery;

  // Mappers
  private final AddressWebMapper addressWebMapper;

  @Operation(summary = "Cambia l'indirizzo del ristorante")
  @PutMapping
  public ResponseEntity<AddressResource> changeRestaurantAddress(
      @PathVariable UUID restaurantId, @RequestBody @Valid ChangeRestaurantAddressRequest request) {

    var command =
        ChangeRestaurantAddressCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .addressName(request.displayName())
            .coordinates(request.coordinates())
            .build();

    Address theUpdatedAddress =
        changeRestaurantAddressUseCase.changeRestaurantAddress(command).getAddress();

    // Crafting response
    var response = addressWebMapper.toResource(theUpdatedAddress);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Leggi l'indirizzo di un ristorante")
  @GetMapping
  public ResponseEntity<AddressResource> showRestaurantAddress(@PathVariable UUID restaurantId) {

    // Fetching Required Data
    var command =
        ShowRestaurantCommand.builder().restaurantId(RestaurantId.from(restaurantId)).build();

    // Calling UseCase/Query
    Address theUpdatedAddress = showRestaurantQuery.showRestaurant(command).getAddress();

    // Crafting response
    var response = addressWebMapper.toResource(theUpdatedAddress);
    return ResponseEntity.ok(response);
  }

  public record ChangeRestaurantAddressRequest(
      @NotNull @NotBlank String displayName, Optional<Coordinates> coordinates) {}
}
