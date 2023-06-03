package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.commons.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.AddCuisineToRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.AddCuisineToRestaurantUseCase.AddCuisineToRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.CreateRestaurantUseCase.CreateRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.DeleteRestaurantUseCase.DeleteRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery.ListRestaurantsCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.RemoveCuisineFromRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.RemoveCuisineFromRestaurantUseCase.RemoveCuisineFromRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.RetrieveRestaurantLogoUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.RetrieveRestaurantLogoUseCase.RetrieveRestaurantLogoCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantQuery.ShowRestaurantCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.StoreRestaurantLogoUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.StoreRestaurantLogoUseCase.StoreRestaurantLogoCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import it.luzzetti.justdrink.backoffice.domain.vo.Extension;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.MenuRestControllerAdapter;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.CuisineAdditionRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.ListRestaurantsResponse;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.RestaurantCreationRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.RestaurantResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.CuisineWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.RestaurantWebMapper;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/1.0/restaurants")
@Log4j2
@RequiredArgsConstructor
public class RestaurantRestControllerAdapter {

  // UseCases
  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final DeleteRestaurantUseCase deleteRestaurantUseCase;
  private final AddCuisineToRestaurantUseCase addCuisineToRestaurantUseCase;
  private final RemoveCuisineFromRestaurantUseCase removeCuisineFromRestaurantUseCase;
  private final StoreRestaurantLogoUseCase storeRestaurantLogoUseCase;
  private final RetrieveRestaurantLogoUseCase retrieveRestaurantLogoUseCase;

  // Queries
  private final ListRestaurantsQuery listRestaurantsQuery;
  private final ShowRestaurantQuery showRestaurantQuery;

  // Mappers
  private final RestaurantWebMapper restaurantWebMapper;
  private final CuisineWebMapper cuisineWebMapper;

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
      @RequestParam Optional<Integer> pageSize,
      @RequestParam Optional<Integer> pageNumber) {

    // Fetching needed data

    var command =
        ListRestaurantsCommand.builder()
            .filter(filter.orElse(""))
            .pageSize(pageSize.orElse(10))
            .offset(pageNumber.orElse(0))
            .build();

    // Calling UseCase
    var restaurants =
        listRestaurantsQuery.listRestaurants(command).stream()
            .map(restaurantWebMapper::toListElement)
            .toList();

    // Crafting a response

    var response = ListRestaurantsResponse.builder().restaurants(restaurants).build();
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

  @Operation(summary = "Esegue la creazione di un nuovo ristorante")
  @PostMapping
  public ResponseEntity<RestaurantResource> createRestaurant(
      @RequestBody @Valid RestaurantCreationRequest request) {

    Set<Cuisine> theCuisines =
        request.cuisines().stream().map(cuisineWebMapper::toDomain).collect(Collectors.toSet());

    // Creating the command
    var command =
        CreateRestaurantCommand.builder()
            .name(request.name())
            .addressName(request.addressName())
            .coordinates(request.coordinates())
            .cuisines(theCuisines)
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

  @Operation(summary = "Esegue l'aggiunta di una cuisine ad un ristorante")
  @PostMapping("/{restaurantId}/cuisines")
  public ResponseEntity<Void> addCuisineToRestaurant(
      @PathVariable UUID restaurantId, @RequestBody @Valid CuisineAdditionRequest request) {

    var command =
        AddCuisineToRestaurantCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .theCuisineToAdd(Cuisine.of(request.name()))
            .build();

    addCuisineToRestaurantUseCase.addCuisineToRestaurant(command);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Esegue l'aggiunta di una cuisine ad un ristorante")
  @DeleteMapping("/{restaurantId}/cuisines/{cuisineName}")
  public ResponseEntity<Void> removeCuisineFromRestaurant(
      @PathVariable UUID restaurantId, @PathVariable String cuisineName) {

    var command =
        RemoveCuisineFromRestaurantCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .cuisineToRemove(Cuisine.of(cuisineName))
            .build();

    removeCuisineFromRestaurantUseCase.removeCuisineFromRestaurant(command);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Permette l'upload del logo di un ristorante")
  @PostMapping("/{restaurantId}/logo:upload")
  public void logoUpload(
      @PathVariable UUID restaurantId, @RequestParam("logo") MultipartFile theMultipartLogo) {

    String contentType = theMultipartLogo.getContentType();
    String originalFileName = theMultipartLogo.getOriginalFilename();

    if (originalFileName == null) {
      throw new ElementNotProcessableException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }

    if (theMultipartLogo.isEmpty()) {
      throw new ElementNotValidException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }

    if (contentType == null) {
      throw new ElementNotValidException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }

    if (!contentType.startsWith("image/")) {
      throw new ElementNotValidException(RestaurantErrors.LOGO_WRONG_CONTENT_TYPE);
    }

    byte[] logo;
    try {
      logo = theMultipartLogo.getBytes();
    } catch (IOException exception) {
      throw new ElementNotValidException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }

    /*
     * In realtà questa logica (tagliare il nome per trovare l'estensione) potrebbe
     * essere messa all'interno della classe Extensions
     */
    int indexOfLastDot = originalFileName.lastIndexOf('.');
    Extension extension = Extension.from(originalFileName.substring(indexOfLastDot));

    StoreRestaurantLogoCommand command =
        StoreRestaurantLogoCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .file(logo)
            .extension(extension)
            .build();

    storeRestaurantLogoUseCase.storeRestaurantLogo(command);
  }

  @Operation(summary = "Permette il download del logo del ristorante")
  @GetMapping(
      value = "/{restaurantId}/logo:download",
      produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<byte[]> downloadLogo(@PathVariable UUID restaurantId) {

    RetrieveRestaurantLogoCommand command =
        RetrieveRestaurantLogoCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .build();

    byte[] logo = retrieveRestaurantLogoUseCase.retrieveRestaurantLogo(command);

    return ResponseEntity.ok(logo);
  }
}
