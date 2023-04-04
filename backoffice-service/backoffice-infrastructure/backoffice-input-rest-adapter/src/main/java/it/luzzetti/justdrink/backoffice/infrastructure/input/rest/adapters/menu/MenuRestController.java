package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuSectionCreationRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuSectionResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.ProductCreationRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.ProductMoveRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.ProductResource;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface MenuRestController {
  public ResponseEntity<MenuResource> getMenu(@PathVariable UUID restaurantId);

  ResponseEntity<List<MenuSectionResource>> listMenuSections(@PathVariable UUID restaurantId);

  ResponseEntity<MenuSectionResource> createMenuSection(
      @PathVariable UUID restaurantId, @RequestBody MenuSectionCreationRequest request);

  ResponseEntity<Void> deleteMenuSection(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId);

  /*
   * PRODUCTS
   */
  @ApiResponses(value = {@ApiResponse(description = "Lista dei prodotti per la sezione richiesta",responseCode = "200")})
  @Operation(summary = "Elenca i prodotti presenti in una determinata sezione del Menu")
  ResponseEntity<List<ProductResource>> listProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId);

  @ApiResponses(value = {@ApiResponse(description = "Mostra il prodotto", responseCode = "200")})
  ResponseEntity<ProductResource> showProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId, @PathVariable UUID productId);

  ResponseEntity<ProductResource> createProduct(
      @PathVariable UUID restaurantId,
      @PathVariable UUID sectionId,
      @RequestBody ProductCreationRequest request);

  ResponseEntity<ProductResource> moveProduct(
      @PathVariable UUID restaurantId,
      @PathVariable UUID sectionId,
      @RequestBody ProductMoveRequest request);

  @Operation(summary ="Esegue l'eliminazione di un prodotto di una sezione del menu, in base al productId")
  @Parameter(name = "restaurantId", description = "UUID restaurant", example = "1")
  @Parameter(name = "sectionId", description = "UUID sezione del menu", example = "1")
  @Parameter(name = "productId", description = "UUID del prodotto", example = "1")
  ResponseEntity<Void> deleteProduct(
      @PathVariable UUID restaurantId, @PathVariable UUID sectionId, @PathVariable UUID productId);
}
