package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ChangeRestaurantAddressUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ChangeRestaurantAddressUseCase.ChangeRestaurantAddressCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase.CreateRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase.DeleteRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery.ListRestaurantsCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery.ShowRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoRestaurantUseCase.UploadFileImageCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.MenuRestControllerAdapter;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.LogoRestaurantResources;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.RestaurantResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.RestaurantWebMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/1.0/restaurants")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class RestaurantRestControllerAdapter {

  // UseCases
  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final ChangeRestaurantAddressUseCase changeRestaurantAddressUseCase;
  private final DeleteRestaurantUseCase deleteRestaurantUseCase;
  private final UploadLogoRestaurantUseCase uploadLogoRestaurantUseCase;

  // Queries
  private final ListRestaurantsQuery listRestaurantsQuery;
  private final ShowRestaurantQuery showRestaurantQuery;

  // Mappers
  private final RestaurantWebMapper restaurantWebMapper;

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

  @Operation(summary = "Mostra la lista di ristoranti presenti")
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

  @Operation(summary = "Mostra il dettaglio di un ristorante in base al restaurantId")
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

  @PutMapping("/{restaurantId}/address")
  public ResponseEntity<RestaurantResource> changeRestaurantAddress(
      @PathVariable UUID restaurantId, @RequestBody @Valid ChangeRestaurantAddressRequest request) {

    var command =
        ChangeRestaurantAddressCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .addressName(request.addressName())
            .coordinates(request.coordinates())
            .build();

    Restaurant thePartiallyUpdatedRestaurant =
        changeRestaurantAddressUseCase.changeRestaurantAddress(command);

    // Crafting response
    var response = restaurantWebMapper.toResource(thePartiallyUpdatedRestaurant);
    return ResponseEntity.ok(response);
  }

  public record ChangeRestaurantAddressRequest(
      @NotNull @NotBlank String addressName, Optional<Coordinates> coordinates) {}

  @Operation(summary = "Esegue la creazione di un nuovo ristorante")
  @PostMapping
  public ResponseEntity<RestaurantResource> createRestaurant(
      @RequestBody @Valid RestaurantCreationRequest request) {
    // Creating the command
    var command =
        CreateRestaurantCommand.builder()
            .name(request.name())
            .addressName(request.addressName())
            .coordinates(request.coordinates())
            .build();

    // Executing Use-Case
    Restaurant theCreatedRestaurant = createRestaurantUseCase.createRestaurant(command);

    // Crafting and returning a resource with HATEOAS
    RestaurantResource resource = restaurantWebMapper.toResource(theCreatedRestaurant);
    addStandardHateoasLinks(theCreatedRestaurant.getId().id(), resource);

    return ResponseEntity.ok(resource);
  }

  @Operation(summary = "Esegue l'eliminazione di un ristorante, in base al restaurantId")
  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID restaurantId) {
    // Creating the command
    var command =
        DeleteRestaurantCommand.builder().restaurantId(RestaurantId.from(restaurantId)).build();

    // Executing Use-Case
    deleteRestaurantUseCase.deleteRestaurant(command);

    // Returning a NO CONTENT
    return ResponseEntity.noContent().build();
  }

  @Builder
  public record ListRestaurantsResponse(
      List<RestaurantListElement> restaurants, String nextPageToken) {}

  public record RestaurantListElement(UUID id, String name) {}

  public record RestaurantCreationRequest(
      @NotNull @NotBlank String name,
      @NotNull @NotBlank String addressName,
      Optional<Coordinates> coordinates) {}

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

  @PostMapping("/{restaurantId}/upload")
  public ResponseEntity<Void> uploadFile(@PathVariable UUID restaurantId,@RequestParam("image") MultipartFile file) {
    String message = "";
    try {
      // storageService.save(file);


      UploadFileImageCommand command = UploadFileImageCommand.builder()
          .restaurantId(RestaurantId.from(restaurantId))
          .inputStream(file.getInputStream())
          .name(file.getOriginalFilename())
          .build();

      String urlLogo = uploadLogoRestaurantUseCase.updateLogo(command);

      message = "Caricamento immagine avvenuto correttamente: " + file.getOriginalFilename();

      log.warn(()-> "Url logo: "+urlLogo);

      return ResponseEntity.noContent().build();

    } catch (Exception e) {
      String error =
          "Non è stato possibile caricare il file: "
              + file.getOriginalFilename()
              + ". Error: "
              + e.getMessage();

      log.warn(()-> error);

      return ResponseEntity.badRequest().build();
    }
  }
}
