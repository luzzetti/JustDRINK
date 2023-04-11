package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

public interface ListProductsOfMenuSectionQuery {

  List<Product> listProductsOfMenuSection(ListProductsOfMenuSectionCommand command);

  @Builder
  record ListProductsOfMenuSectionCommand(
      @NotNull RestaurantId restaurantId, @NotNull MenuSectionId menuSectionId) {}
}
