package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateMenuSectionUseCase.CreateMenuSectionCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteMenuSectionUseCase.DeleteMenuSectionCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListMenuSectionsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListMenuSectionsQuery.ListMenuSectionsCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListProductsOfMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListProductsOfMenuSectionQuery.ListProductsOfmenuSectionCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery.ShowMenuCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.RestaurantRestControllerAdapter;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.MenuSectionWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.MenuWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.ProductWebMapper;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
public class MenuRestControllerAdapter {

  private final ShowMenuQuery showMenuQuery;
  private final ListMenuSectionsQuery listMenuSectionsQuery;
  private final CreateMenuSectionUseCase createMenuSectionUseCase;
  private final DeleteMenuSectionUseCase deleteMenuSectionUseCase;
  private final CreateProductUseCase createProductUseCase;
  private final MenuWebMapper menuWebMapper;
  private final MenuSectionWebMapper menuSectionWebMapper;
  private final ProductWebMapper productWebMapper;

  private final ListProductsOfMenuSectionQuery listProductsOfMenuSectionQuery;


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

  @GetMapping("/sections/{sectionId}/products")
  public ResponseEntity<List<ProductResource>> listProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId) {

    var command = ListProductsOfmenuSectionCommand.builder().restaurantId(restaurantId)
        .menuSectionId(sectionId).build();

    var response = listProductsOfMenuSectionQuery.listProductsOfMenuSection(command).stream()
        .map(productWebMapper::toResource).toList();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/sections/{sectionId}/products/{productId}")
  public ResponseEntity<List<ProductResource>> showProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId, @PathVariable UUID productId) {

    // TODO: da implementare
    throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
  }

  @PostMapping("/sections/{sectionId}/products")
  public ResponseEntity<ProductResource> createProduct(
      @PathVariable UUID restaurantId,
      @PathVariable UUID sectionId,
      @RequestBody ProductCreationRequest request) {

    Product theCreatedProduct =
        createProductUseCase.createProduct(
            request.name(),
            request.price(),
            RestaurantId.from(restaurantId),
            MenuSectionId.from(sectionId));

    var response = productWebMapper.toResource(theCreatedProduct);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/sections/{sectionId}/products:move")
  public ResponseEntity<ProductResource> moveProduct(
      @PathVariable UUID restaurantId,
      @PathVariable UUID sectionId,
      @RequestBody ProductMoveRequest request) {

    // TODO: da implementare
    throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
  }

  @DeleteMapping("/sections/{sectionId}/products/{productId}")
  public ResponseEntity<Void> deleteProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId, @PathVariable UUID productId) {

    // TODO: da implementare
    throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
  }

  private void addStandardHateoasLinks(MenuSectionResource resource) {
    log.debug(() -> "Implementare %s".formatted(resource));
  }

  // POST :reset

  private static void addStandardHateoasLinks(UUID restaurantId, MenuResource resource) {

    var thisAdapter = MenuRestControllerAdapter.class;
    var restaurantAdapter = RestaurantRestControllerAdapter.class;

    resource.add(
        linkTo(methodOn(thisAdapter).getMenu(restaurantId)).withSelfRel(),
        linkTo(methodOn(thisAdapter).listMenuSections(restaurantId)).withRel("MenuSections"),
        linkTo(methodOn(restaurantAdapter).getRestaurant(restaurantId)).withRel("Restaurant"));
  }
}
