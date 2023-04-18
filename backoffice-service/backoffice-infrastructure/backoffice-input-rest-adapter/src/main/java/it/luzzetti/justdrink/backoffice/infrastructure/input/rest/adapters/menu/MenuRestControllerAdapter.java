package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateMenuSectionUseCase.CreateMenuSectionCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateProductUseCase.CreateProductCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteMenuSectionUseCase.DeleteMenuSectionCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteProductFromMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteProductFromMenuSectionUseCase.DeleteProductCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListMenuSectionsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListMenuSectionsQuery.ListMenuSectionsCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListProductsOfMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListProductsOfMenuSectionQuery.ListProductsOfMenuSectionCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.MoveProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.MoveProductUseCase.MoveProductCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery.ShowMenuCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowProductFromMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowProductFromMenuSectionQuery.ShowProductOfMenuSectionCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuSectionCreationRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuSectionResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.ProductCreationRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.ProductMoveRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.ProductResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.RestaurantRestControllerAdapter;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.MenuSectionWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.MenuWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.ProductWebMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * This is a 'Singleton sub-resource' like shown in 'API Design Patterns'
 */

@RestController
@RequestMapping("/api/1.0/restaurants/{restaurantId}/menu")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class MenuRestControllerAdapter implements MenuRestController {

  // UseCases
  private final CreateMenuSectionUseCase createMenuSectionUseCase;
  private final DeleteMenuSectionUseCase deleteMenuSectionUseCase;
  private final CreateProductUseCase createProductUseCase;
  private final DeleteProductFromMenuSectionUseCase deleteProductFromMenuSectionUseCase;
  private final MoveProductUseCase moveProductUseCase;

  // Queries
  private final ShowMenuQuery showMenuQuery;
  private final ListMenuSectionsQuery listMenuSectionsQuery;
  private final ShowProductFromMenuSectionQuery showProductFromMenuSectionQuery;
  private final ListProductsOfMenuSectionQuery listProductsOfMenuSectionQuery;

  // Mappers
  private final MenuWebMapper menuWebMapper;
  private final MenuSectionWebMapper menuSectionWebMapper;
  private final ProductWebMapper productWebMapper;

  private static void addStandardHateoasLinks(UUID restaurantId, MenuResource resource) {

    var thisAdapter = MenuRestControllerAdapter.class;
    var restaurantAdapter = RestaurantRestControllerAdapter.class;

    resource.add(
        linkTo(methodOn(thisAdapter).getMenu(restaurantId)).withSelfRel(),
        linkTo(methodOn(thisAdapter).listMenuSections(restaurantId)).withRel("MenuSections"),
        linkTo(methodOn(restaurantAdapter).getRestaurant(restaurantId)).withRel("Restaurant"));
  }

  @Override
  @GetMapping
  public ResponseEntity<MenuResource> getMenu(@PathVariable UUID restaurantId) {
    // Parsing parameters from HTTP
    var command = ShowMenuCommand.builder().restaurantId(new RestaurantId(restaurantId)).build();

    // Calling Use-Case
    Menu theFoundMenu = showMenuQuery.showMenu(command);

    // Crafting a HATEOAS response
    MenuResource resource = menuWebMapper.toResource(theFoundMenu);
    addStandardHateoasLinks(restaurantId, resource);

    return ResponseEntity.ok(resource);
  }

  @Override
  @GetMapping("/sections")
  public ResponseEntity<List<MenuSectionResource>> listMenuSections(
      @PathVariable UUID restaurantId) {

    var command = ListMenuSectionsCommand.builder().restaurantId(restaurantId).build();

    var response =
        listMenuSectionsQuery.listMenuSections(command).stream()
            .map(menuSectionWebMapper::toResource)
            .toList();

    return ResponseEntity.ok(response);
  }

  @Override
  @PostMapping("/sections")
  public ResponseEntity<MenuSectionResource> createMenuSection(
      @PathVariable UUID restaurantId, @RequestBody MenuSectionCreationRequest request) {

    var command =
        CreateMenuSectionCommand.builder()
            .restaurantId(restaurantId)
            .title(request.title().orElse("a new section")) // Extract and i18nalize
            .build();

    MenuSection theCreatedMenuSection = createMenuSectionUseCase.createMenuSection(command);

    MenuSectionResource resource = menuSectionWebMapper.toResource(theCreatedMenuSection);
    addStandardHateoasLinks(resource);

    return ResponseEntity.ok(resource);
  }

  @Override
  @DeleteMapping("/sections/{sectionId}")
  public ResponseEntity<Void> deleteMenuSection(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId) {

    var command =
        DeleteMenuSectionCommand.builder()
            .restaurantId(restaurantId)
            .menuSectionId(sectionId) // Extract and i18nalize
            .build();

    deleteMenuSectionUseCase.deleteMenuSection(command);

    return ResponseEntity.noContent().build();
  }

  /*
   * PRODUCTS
   */
  @Override
  @GetMapping("/sections/{sectionId}/products")
  public ResponseEntity<List<ProductResource>> listProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId) {

    // Parsing the Http-request
    var command =
        ListProductsOfMenuSectionCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .menuSectionId(MenuSectionId.from(sectionId))
            .build();

    // Calling the appropriate Use-Case
    List<Product> theFoundProducts =
        listProductsOfMenuSectionQuery.listProductsOfMenuSection(command);

    // Crafting a response
    var response = theFoundProducts.stream().map(productWebMapper::toResource).toList();
    return ResponseEntity.ok(response);
  }

  @Override
  @GetMapping("/sections/{sectionId}/products/{productId}")
  public ResponseEntity<ProductResource> showProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId, @PathVariable UUID productId) {

    // Parsing the Http-request
    var command =
        ShowProductOfMenuSectionCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .menuSectionId(MenuSectionId.from(sectionId))
            .productId(ProductId.from(productId))
            .build();

    // Calling the appropriate Use-Case
    Product product = showProductFromMenuSectionQuery.showProductOfMenuSection(command);

    // Crafting the response
    var response = productWebMapper.toResource(product);
    return ResponseEntity.ok(response);
  }

  @Override
  @PostMapping("/sections/{sectionId}/products")
  public ResponseEntity<ProductResource> createProduct(
      @PathVariable UUID restaurantId,
      @PathVariable UUID sectionId,
      @RequestBody ProductCreationRequest request) {

    // Parsing HTTP-request into a command
    var command =
        CreateProductCommand.builder()
            .name(request.name())
            .price(request.price())
            .restaurantId(RestaurantId.from(restaurantId))
            .sectionId(MenuSectionId.from(sectionId))
            .build();

    // Calling the appropriate use-case
    Product theCreatedProduct = createProductUseCase.createProduct(command);

    // Crafting a response
    var response = productWebMapper.toResource(theCreatedProduct);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  @PostMapping("/sections/{sectionId}/products:move")
  public ResponseEntity<ProductResource> moveProduct(
      @PathVariable UUID restaurantId,
      @PathVariable UUID sectionId,
      @RequestBody ProductMoveRequest request) {

    // Fetching Data - (Creating the command from the Http request)
    var command =
        MoveProductCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .sourceSectionId(MenuSectionId.from(sectionId))
            .targetSectionId(MenuSectionId.from(request.targetMenuSectionId()))
            .productId(ProductId.from(request.productId()))
            .build();

    // Calling the UseCase
    Product product = moveProductUseCase.moveProduct(command);

    // Crafting a response
    var response = productWebMapper.toResource(product);
    return ResponseEntity.ok(response);
  }

  @Override
  @DeleteMapping("/sections/{sectionId}/products/{productId}")
  public ResponseEntity<Void> deleteProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId, @PathVariable UUID productId) {

    DeleteProductCommand command =
        DeleteProductCommand.builder()
            .restaurantId(restaurantId)
            .menuSectionId(sectionId)
            .productId(productId)
            .build();

    deleteProductFromMenuSectionUseCase.deleteProduct(command);

    return ResponseEntity.noContent().build();
  }

  // POST :reset

  private void addStandardHateoasLinks(MenuSectionResource resource) {
    log.debug(() -> "Implementare %s".formatted(resource));
  }
}
