package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery.ShowMenuCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.MenuWebMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final MenuWebMapper menuWebMapper;

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

  // POST :reset

  private static void addStandardHateoasLinks(UUID restaurantId, MenuResource resource) {
    resource.add(
        linkTo(methodOn(MenuRestControllerAdapter.class).getMenu(restaurantId)).withSelfRel(),
        linkTo(methodOn(RestaurantRestControllerAdapter.class).getRestaurant(restaurantId))
            .withRel("getRestaurant"));
  }
}
